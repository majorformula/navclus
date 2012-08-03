/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.monitor.listeners;

import java.util.ArrayList;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.PlugIn;
import navclus.userinterface.classdiagram.actions.JavaAddition;
import navclus.userinterface.classdiagram.actions.RedrawAction;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;
import navclus.userinterface.classdiagram.utils.FlagRedraw;
import navclus.userinterface.classdiagram.utils.JavaEditorUtil;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

public class JavaEditorPartListener2 implements IPartListener2 {


	public void partActivated(IWorkbenchPartReference partRef) {
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {				
	}

	public void partOpened(IWorkbenchPartReference partRef) {	
		final IJavaElement javaelement = JavaEditorUtil.getJavaElement(partRef);
		if (javaelement == null) return;		
		if (javaelement != null) {
			try {
				NavClusView.getDefault().getRootModel().openCU((ICompilationUnit) javaelement);
				NavClusView.getDefault().getSelectionKeeper().addSelection(javaelement);
				(new RedrawAction()).run();
			} catch (JavaModelException e) {
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
		IJavaElement javaelement = JavaEditorUtil.getJavaElement(partRef);
		if (javaelement == null) return;

		try {			
			NavClusView.getDefault().getRootModel().closeCUwoUpdate((ICompilationUnit) javaelement);
			(new RedrawAction()).run();

			//			if (FlagRedraw.isSync() == true) {
			//				synchronizeNodewithTab();
			//			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

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
		= (ArrayList<TypeNode>)(((ArrayList<TypeNode>) NavClusView.getDefault().getRootModel().getRootNode().getTypeNodes()).clone());

		boolean bExist = false;
		for (TypeNode nodepart: nodeparts) {
			bExist = JavaEditorUtil.IsExistInTab(nodepart.getType());

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
		RootNode rootpart = NavClusView.getDefault().getRootModel().getRootNode();
		if (rootpart == null) return;		

		ArrayList<TypeNode> nodeparts 
		= (ArrayList<TypeNode>)(((ArrayList<TypeNode>) rootpart.getTypeNodes()).clone());

		for (TypeNode node: nodeparts) {
			IType type= node.getType();

			// delete the element if the node in a graph does not exist in the tab
			boolean bExist = JavaEditorUtil.IsExistInTab(type);
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
			NavClusView.getDefault().getRootModel().closeCUwoUpdate((ICompilationUnit) _element);
		} else if (_element instanceof IClassFile) {
			NavClusView.getDefault().getRootModel().closeClasswoUpdate((IClassFile) _element);
		} else if (_element instanceof IType) {
			NavClusView.getDefault().getRootModel().deleteTypewithChildrenwoUpdate((IType) _element);
		} else if (_element instanceof IMember) {
			_element = _element.getAncestor(IJavaElement.TYPE);
			NavClusView.getDefault().getRootModel().deleteTypewithChildrenwoUpdate((IType) _element);
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
		bCurrentExist = JavaEditorUtil.IsExistInTab(curJavaElement);

		// add the element if the node does not exist in the graph
		if (bCurrentExist == true) {
			JavaAddition addingJob = new JavaAddition(curJavaElement, NavClusView.getDefault().getRootModel());
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
