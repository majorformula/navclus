
package navclus.userinterface.classdiagram.listeners;

import java.util.Iterator;
import java.util.List;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.classfigure.UMLNode;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;
import navclus.userinterface.classdiagram.utils.JavaEditorUtil;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;


public class GraphMouseListener implements org.eclipse.draw2d.MouseListener {


	public GraphMouseListener() {
		super();
	}

	public void mouseDoubleClicked(MouseEvent me) {
			
		if (NavClusView.getDefault() == null) return; 
		if (NavClusView.getDefault().getG() == null) return;
		
		RootNode root = NavClusView.getDefault().getRootNode();	
		
		Point mousePoint = new Point(me.x, me.y);
		NavClusView.getDefault().getG().getRootLayer().translateToRelative(mousePoint);
		NavClusView.getDefault().getG().getRootLayer().translateFromParent(mousePoint);
		
		List selectedItems = NavClusView.getDefault().getG().getSelection();
		
		if (selectedItems.size() > 0) {
			Iterator iterator = selectedItems.iterator();
			while (iterator.hasNext()) {
				GraphItem item = (GraphItem) iterator.next();
				if (item.getItemType() == GraphItem.NODE) {
						
					UMLNode node = (UMLNode) item;
					
					// Bring to Top if node.getText means IType
					if ((node.getText() == null) || (node.getText() == ""))
						return;
					
					TypeNode typenode = root.getTypeNode(node);
					if (typenode == null) return;					
					
					IType type = typenode.getType(); 		
					if (type == null) return;
								
					// test
					try {
						try {
							locateCode(node, type, me.y);
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
					
				} else {
					// There is no movement for connection
				}
			}
		}			
	}
	
	private void locateCode(UMLNode node, IType type, int mine) throws JavaModelException, PartInitException {
		IJavaElement element = JavaEditorUtil.getJavaElement(type);
		if (element == null) return;
		
		IEditorPart javaEditor;
		if (JavaEditorUtil.IsExistInTab(element))
			javaEditor = JavaEditorUtil.bringToTop(element);
		else
			javaEditor = JavaUI.openInEditor(element);
				
		String selectedtext = null;
		IFigure figure = node.getNodeFigure();		
		List<IFigure> childrenfigures = figure.getChildren();		
		for (IFigure child: childrenfigures) {			
			List<IFigure> grandchildrenfigures = child.getChildren();			
			for (IFigure grandchild: grandchildrenfigures) {				
				if (grandchild instanceof Label) {
					if (((((Label)grandchild).getLocation().y + 18) > mine) 
						&& ((((Label)grandchild).getLocation().y ) <= mine)) 
					{
						selectedtext = ((Label)grandchild).getText();
						break;
					}
				}
			}						
		}
		
		if (selectedtext == null) return;	
		IJavaElement[] children = type.getChildren();
		for (IJavaElement child: children) {
			if (selectedtext.contains(child.getElementName()))
				JavaUI.revealInEditor(javaEditor, (IJavaElement) child);
		}
	}

	public void mousePressed(MouseEvent me) {	
	}

	public void mouseReleased(MouseEvent me) {		
	}
}
