package navclus.userinterface.classdiagram.actions;
/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

import java.util.List;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.classfigure.ClassFigureCreator;
import navclus.userinterface.classdiagram.classfigure.UMLNode;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jface.action.IAction;
//import org.eclipse.mylyn.monitor.core.InteractionEvent;
//import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;


public class RedrawActionwoLayout extends RedrawAction implements IAction {

//	public RedrawActionwoLayout(String Title, String Icon, NavClusView viewer) {
////		super(Title, Icon, viewer);
//	}


	@SuppressWarnings("restriction")
	public void run() {		
		System.out.println("!!!!! run in RedrawActionwoLayout !!!!!");
		
		if (this.isChecked() == false) return;

//		if (bLog) {
//			viewer.setDrawOption(this.drawOption);			
//		}

		boolean bTop = false;
		IJavaElement topElement = EditorUtility.getActiveEditorJavaInput();

		RootNode root = NavClusView.getDefault().getRootModel().getRootNode();
		
		List<TypeNode> nodes = (List<TypeNode>) root.getTypeNodes(); // salee - error
		for (TypeNode preNode: nodes) { // it is modifiable
			UMLNode preGraphNode = root.getUMLNode(preNode);
			if (preGraphNode == null) continue;
			Point prePoint = preGraphNode.getLocation();
			
			IType preType = preNode.getType();
			if (preType == null) continue;

			if (topElement == null)
				bTop = false;
			else if (preType.getTypeRoot().getHandleIdentifier().equals(topElement.getHandleIdentifier()))
				bTop = true;
			else
				bTop = false;
			
			// setCustomFigure
			IFigure classFigure = (new ClassFigureCreator()).createClassFigure(preNode);
			if (classFigure == null) continue;
			
			preGraphNode.setUMLNode(NavClusView.getDefault().getG(), classFigure);			
			preGraphNode.setText((preType.getHandleIdentifier()));
			preGraphNode.setLocation(prePoint.x, prePoint.y);
		}
				
		// remove all connections
		root.removeAllGraphConnections();
		
		// add all connections
		root.drawStructuralRelations();
		
		NavClusView.getDefault().getG().applyNoLayout();					
//		super.log();
	}
}
