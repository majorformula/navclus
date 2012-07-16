///*******************************************************************************
// * Copyright (c) 2007 UBC, SPL
// * All rights reserved.
// * 
// * Contributors:
// *     Seonah Lee - initial implementation
// *******************************************************************************/
//
//package ca.ubc.cs.salee.classdiagram.actions;
//
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;
//import org.eclipse.core.runtime.jobs.Job;
//import org.eclipse.jdt.core.IClassFile;
//import org.eclipse.jdt.core.ICompilationUnit;
//import org.eclipse.jdt.core.IJavaElement;
//import org.eclipse.jdt.core.IMember;
//import org.eclipse.jdt.core.IType;
//import org.eclipse.jdt.core.JavaModelException;
//import org.eclipse.jface.dialogs.MessageDialog;
//
//import ca.ubc.cs.salee.classdiagram.PlugIn;
//import ca.ubc.cs.salee.classdiagram.java.analyzer.RootModel;
//import ca.ubc.cs.salee.classdiagram.java.analyzer.TypeModel;
//
//public class JavaAddition extends Job  {
//
//	public static final String MY_FAMILY = "addingJobFamily";
//    public boolean belongsTo(Object family) {
//        return family == MY_FAMILY;
//     }
//	
//	IJavaElement _element;
//	RootModel rootmodel;
//    
//	public JavaAddition(IJavaElement _element, RootModel rootmodel) {
//		super("AddingJob");
//		this._element = _element;
//		this.rootmodel = rootmodel;
//	}
//
//	public IStatus run(IProgressMonitor monitor) {
//		
////		typemodel.deleteTemporaryPart(); // salee - move the place
//
//		if (_element instanceof ICompilationUnit) {
//			try {
//				rootmodel.openCU((ICompilationUnit) _element);
//			} catch (JavaModelException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (_element instanceof IClassFile) {
//			try {
//				rootmodel.openClass((IClassFile) _element);
//			} catch (JavaModelException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (_element instanceof IType) {
//			System.out.println("type:" + _element.getElementName());
//			rootmodel.createType((IType) _element);
//			
//		} else if (_element instanceof IMember) {
//			
////			System.out.println("member:" + _element.getElementName());
//			IJavaElement type_element = _element.getAncestor(IJavaElement.TYPE);
//			
////			if (!rootmodel.contain((IType) type_element)) {
//				rootmodel.createType((IType) type_element);
////			}
////			System.out.println("member:" + _element.getElementName());
////			rootmodel.addElement(_element);
//		} else {
//			MessageDialog.openInformation(PlugIn.getDefaultShell(),
//					"Error", 
//					"Cannot open this kind of Java Element:" + _element);
//		}
//		
//		return Status.OK_STATUS;
//	}	
//}
