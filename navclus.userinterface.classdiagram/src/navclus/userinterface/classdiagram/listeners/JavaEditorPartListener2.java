package navclus.userinterface.classdiagram.listeners;
/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.mylyn.monitor.core.InteractionEvent;
//import org.eclipse.mylyn.monitor.core.InteractionEvent.Kind;
//import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

import navclus.userinterface.classdiagram.PlugIn;
import navclus.userinterface.classdiagram.actions.JavaAddition;
import navclus.userinterface.classdiagram.actions.RedrawAction;
//import navclus.userinterface.classdiagram.actions.RedrawActionwoLayout;
import navclus.userinterface.classdiagram.java.analyzer.RootModel;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;
import navclus.userinterface.classdiagram.utils.FlagRedraw;
import navclus.userinterface.classdiagram.utils.JavaEditorUtil;
import navclus.userinterface.classdiagram.utils.TypeHistory;
import navclus.userinterface.classdiagram.NavClusView;

public class JavaEditorPartListener2 implements IPartListener2 {
	
	NavClusView viewer ;	
	RootModel rootmodel;		
	JavaEditorUtil javaeditorutil ;
	
	public JavaEditorPartListener2(NavClusView viewer) {
		super();		
		this.viewer = viewer;	
		this.rootmodel = viewer.getRootModel();
		this.javaeditorutil = new JavaEditorUtil();
	}
	
	public void partActivated(IWorkbenchPartReference partRef) {
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {				
	}

	public void partOpened(IWorkbenchPartReference partRef) {	
		final IJavaElement javaelement = javaeditorutil.getJavaElement(partRef);
		if (javaelement == null) return;		
		if (javaelement != null) {
//			System.out.println("open:" + javaelement);
			try {
				this.rootmodel.openCU((ICompilationUnit) javaelement);
				(new RedrawAction(viewer)).run();
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			JavaAddition addingJob = new JavaAddition(javaelement, rootmodel);
//			addingJob.setPriority(Job.BUILD);
//			addingJob.schedule();
//			addCurrentNode(javaelement);	
		}
		else 
			System.err.println("<exception occurs - partOpened>");
							
//		InteractionEvent interactionEvent 
//		= new InteractionEvent(
//				InteractionEvent.Kind.PREFERENCE, // kind
//				"null",    // structureKind
//				javaelement.getHandleIdentifier().hashCode() + " ", // handle
//				viewer.ID, // originId
//				"null",	   // navigatedRelation
//				"classview: open_class: " + viewer.countGraphNodes(), // delta
//				1f);
//				
//		MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);		
	}	
	
	public void partClosed(IWorkbenchPartReference partRef) {
		IJavaElement javaelement = javaeditorutil.getJavaElement(partRef);
		if (javaelement == null) return;
		
//		try {			
////			deleteJavaFile(javaelement);
//			
//			if (FlagRedraw.isSync() == true) {
//				synchronizeNodewithTab();
//			}
//		} catch (JavaModelException e) {
//			e.printStackTrace();
//		}
				
//		InteractionEvent interactionEvent 
//		= new InteractionEvent(
//				InteractionEvent.Kind.PREFERENCE, // kind
//				"null",    // structureKind
//				javaelement.getHandleIdentifier().hashCode() + " ", // handle
//				viewer.ID, // originId
//				"null",	   // navigatedRelation
//				"classview ; close_class ; " + viewer.countGraphNodes(), // delta
//				1f);
//				
//		MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);
	}
	public void partDeactivated(IWorkbenchPartReference partRef) {
	}
	public void partHidden(IWorkbenchPartReference partRef) {
	}
	public void partVisible(IWorkbenchPartReference partRef) {
	}
	
	public void partInputChanged(IWorkbenchPartReference partRef) {
//		IJavaElement javaelement = javaeditorutil.getJavaElement(partRef);
//		if (javaelement == null) return;
//
//		removePreviousNode();
//		addCurrentNode(javaelement);					
	}

	///////////////////////////////////////////////////////////////////////////////		
//	private void deleteJavaFile(IJavaElement _element) throws JavaModelException {		
////		typemodel.deleteTemporaryPart();	
//
//		if (_element instanceof ICompilationUnit) {
//			rootmodel.closeCU((ICompilationUnit) _element);
//		} else if (_element instanceof IClassFile) {
//			rootmodel.closeClass((IClassFile) _element);
//		} else if (_element instanceof IType) {
//			rootmodel.deleteTypewithChildren((IType) _element);
//		} else if (_element instanceof IMember) {
//			_element = _element.getAncestor(IJavaElement.TYPE);
//			rootmodel.deleteTypewithChildren((IType) _element);
//		} else {
//			MessageDialog.openInformation(PlugIn.getDefaultShell(),
//					"Error", 
//					"Cannot open this kind of Java Element:" + _element);
//		}
//	}

	private void synchronizeNodewithTab() {
		ArrayList<TypeNode> nodeparts 
			= (ArrayList<TypeNode>)(((ArrayList<TypeNode>) rootmodel.getRootNode().getTypeNodes()).clone());
		
		boolean bExist = false;
		for (TypeNode nodepart: nodeparts) {
			bExist = javaeditorutil.IsExistInTab(nodepart.getType());
			
			if (!bExist) {
//				try {
////					deleteJavaFile((IJavaElement) nodepart.getType());
//				} catch (JavaModelException e) {
//					e.printStackTrace();
//				}
			}			
		}		
	}
	///////////////////////////////////////////////////////////////////////////////	
	
	// delete if previous element does not exist in the tabs & the matched node exists
	private void removePreviousNode() {
		RootNode rootpart = rootmodel.getRootNode();
		if (rootpart == null) return;		

		ArrayList<TypeNode> nodeparts 
			= (ArrayList<TypeNode>)(((ArrayList<TypeNode>) rootpart.getTypeNodes()).clone());

		for (TypeNode node: nodeparts) {
			IType type= node.getType();

			// delete the element if the node in a graph does not exist in the tab
			boolean bExist = javaeditorutil.IsExistInTab(type);
			if (bExist == false) { 
				try {		
					deleteJavaFilewoUpdate((IJavaElement) type);				
				} catch (JavaModelException e) {
					e.printStackTrace();
				}
			}				
		}		
	}
	
	private void deleteJavaFilewoUpdate(IJavaElement _element) throws JavaModelException {		
//		typemodel.deleteTemporaryPart();	

		if (_element instanceof ICompilationUnit) {
			rootmodel.closeCUwoUpdate((ICompilationUnit) _element);
		} else if (_element instanceof IClassFile) {
			rootmodel.closeClasswoUpdate((IClassFile) _element);
		} else if (_element instanceof IType) {
			rootmodel.deleteTypewithChildrenwoUpdate((IType) _element);
		} else if (_element instanceof IMember) {
			_element = _element.getAncestor(IJavaElement.TYPE);
			rootmodel.deleteTypewithChildrenwoUpdate((IType) _element);
		} else {
			MessageDialog.openInformation(PlugIn.getDefaultShell(),
					"Error", 
					"Cannot open this kind of Java Element:" + _element);
		}
	}
	

	private boolean addCurrentNode(IJavaElement curJavaElement) {
		System.out.println("add current node");
		
		if (curJavaElement == null) return true;
		
		boolean bCurrentExist = false;
		bCurrentExist = javaeditorutil.IsExistInTab(curJavaElement);

		// add the element if the node does not exist in the graph
		if (bCurrentExist == true) {
			JavaAddition addingJob = new JavaAddition(curJavaElement, rootmodel);
			addingJob.setPriority(Job.BUILD);
			addingJob.schedule();
			return bCurrentExist;
		}
		else { 
			System.err.println("<exception occurs - partActivated:addCurrentNodes>");
			return bCurrentExist;
		}
	}

}