package navclus.userinterface.classdiagram.listeners;
///*******************************************************************************
// * Copyright (c) 2007 UBC, SPL
// * All rights reserved.
// * 
// * Contributors:
// *     Seonah Lee - initial implementation
// *******************************************************************************/
//
//package ca.ubc.cs.salee.classdiagram.listeners;
//
//import org.eclipse.jdt.core.ICodeAssist;
//import org.eclipse.jdt.core.IJavaElement;
//import org.eclipse.jdt.core.IType;
//import org.eclipse.jdt.core.ITypeRoot;
//import org.eclipse.jdt.core.JavaModelException;
//import org.eclipse.jface.text.ITextSelection;
//import org.eclipse.jface.text.TextSelection;
//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.ui.ISelectionListener;
//import org.eclipse.ui.IWorkbenchPart;
//
//import ca.ubc.cs.salee.classdiagram.actions.RedrawActionwoLayout;
//import ca.ubc.cs.salee.classdiagram.java.analyzer.RootModel;
//import ca.ubc.cs.salee.classdiagram.utils.FlagRedraw;
//import ca.ubc.cs.salee.classdiagram.utils.JavaEditorUtil;
//import ca.ubc.cs.salee.classdiagram.view.Viewer;
//
//public class JavaEditorSelectionListener implements ISelectionListener {
//
//	Viewer viewer;	
//	RootModel rootmodel;	
//	JavaEditorUtil javaeditorutil;
//
//	public JavaEditorSelectionListener(Viewer viewer) {
//		this.viewer = viewer;	
//		this.rootmodel = viewer.getRootModel();	
//		this.javaeditorutil = new JavaEditorUtil();		
//	}
//
//	public void selectionChanged(IWorkbenchPart part,
//			ISelection selection) {
//				
//		if (FlagRedraw.isDone()) {
//			FlagRedraw.setDone(false);
//			return;
//		}
//		
//		boolean bUpdate = false;
//
//		IJavaElement topElement = javaeditorutil.getJavaElement(part);		
//		if (topElement == null) return;
//		
//		if (selection instanceof TextSelection) {
//			ITextSelection selectedtext = (ITextSelection) selection;								
//			if (selectedtext.getStartLine() < 1)
//				return;
//			
//
//			
//			IType topType = ((ITypeRoot) topElement).findPrimaryType();
//			try {
//				// do not work if the types of being deleted and created are the same & the type will not be permanent.
//				IJavaElement locElement = ((ITypeRoot) topElement).getElementAt(selectedtext.getOffset());
//				IJavaElement[] javaelements = ((ICodeAssist) topElement).codeSelect(selectedtext.getOffset(), selectedtext.getLength());
//
//				
////				System.out.println("selectionChanged: " );
////				if (locElement != null)
////					System.out.println("---: " + locElement.getElementName());
//				
//				// show the elements in a class figure
//				bUpdate = rootmodel.addElement(topType, locElement);	
////				System.out.println("loc Element is: " + locElement.getElementName());
//
//				if (bUpdate) {
////					System.out.println("redraw when it is selected");
//					(new RedrawActionwoLayout(viewer)).run();
//				}
//
//			} catch (JavaModelException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//}
