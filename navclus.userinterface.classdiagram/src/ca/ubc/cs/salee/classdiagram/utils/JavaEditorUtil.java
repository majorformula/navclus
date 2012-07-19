package ca.ubc.cs.salee.classdiagram.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.internal.ui.javaeditor.IClassFileEditorInput;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import ca.ubc.cs.salee.classdiagram.PlugIn;

public class JavaEditorUtil {

	public boolean IsExistInTab(IType type) {
		if (type == null) return false;
		
		return IsExistInTab(type.getTypeRoot());		
	}
	
	public boolean IsExistInTab(IJavaElement javaElement) {
		if (javaElement == null) return false;

		IWorkbenchWindow WINDOW = PlugIn.getDefault().getWorkbench().getActiveWorkbenchWindow();
		if (WINDOW.getActivePage() == null) return false;

		IEditorReference[] editorreferences = WINDOW.getActivePage().getEditorReferences();
		for (IEditorReference editorRef: editorreferences) {				
			IJavaElement tabElement = getJavaElement(editorRef);
			
			if (tabElement == null)
				continue;
			else {
				if (tabElement.getHandleIdentifier().equals(javaElement.getHandleIdentifier())) {
					return true;
				}				
			}			
		}
		return false;
	}
	
	public IJavaElement getJavaElement(IEditorReference editorreference) {		
		if (editorreference == null) return null; 

		if (editorreference.getId().equals(JavaUI.ID_CU_EDITOR)) {	
			try {
				IFileEditorInput fei;
				fei = (IFileEditorInput) editorreference.getEditorInput();
				IFile file = fei.getFile();
				return JavaCore.create(file);
			} catch (PartInitException e) {
				e.printStackTrace();
			}				
		}
		else if (editorreference.getId().equals(JavaUI.ID_CF_EDITOR)){	
			try {
				IClassFileEditorInput fei;
				fei = (IClassFileEditorInput) editorreference.getEditorInput();
				return fei.getClassFile();
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}		
		return null;
	}
	
	
	public IJavaElement getJavaElement(IWorkbenchPartReference partRef) {
		if (partRef == null) return null;

		if (partRef.getId().equals(JavaUI.ID_CU_EDITOR)) {				
			IEditorPart ep = (IEditorPart) partRef.getPart(true);	
			IFileEditorInput fei = (IFileEditorInput) ep.getEditorInput();
			IFile file = fei.getFile();
			return JavaCore.create(file);
		}
		else if (partRef.getId().equals(JavaUI.ID_CF_EDITOR)) {
			IEditorPart ep = (IEditorPart) partRef.getPart(true);	
			IClassFileEditorInput fei = (IClassFileEditorInput) ep.getEditorInput();
			return fei.getClassFile();
		}
		else
			return null;
	}
		
	public IJavaElement getJavaElement(IType type) {
		return type.getTypeRoot();
	}
	
	
	public IEditorPart bringToTop(IJavaElement javaElement) {
		IWorkbenchWindow WINDOW = PlugIn.getDefault().getWorkbench().getActiveWorkbenchWindow();
		if (WINDOW.getActivePage() == null) return null;

		IEditorReference[] editorreferences = WINDOW.getActivePage().getEditorReferences();

		for (IEditorReference editorRef: editorreferences) {				
			IEditorPart editorPart = editorRef.getEditor(true);				

			IJavaElement tabElement = getJavaElement(editorRef);

			if (tabElement != null) {
				if (tabElement.getHandleIdentifier().equals(javaElement.getHandleIdentifier())) {
					WINDOW.getActivePage().bringToTop(editorPart);						
					return editorPart;
				}
			}
			else 
				System.err.println("<exception occurs - partActivated:deletePreviousNodes>");				
		}
		return null;
	}
	
	
	public IJavaElement getTopElement() {

		IWorkbenchWindow WINDOW = PlugIn.getDefault().getWorkbench().getActiveWorkbenchWindow();

		if (WINDOW.getActivePage() != null) {
			IEditorPart editorPart = WINDOW.getActivePage().getActiveEditor(); 
			
			if (editorPart != null)
				return getJavaElement(editorPart); 
		}
		return null;
	}
	
	public IJavaElement getJavaElement(IEditorPart editor) {
		if (editor == null) return null;

		if (editor.getEditorSite().getId().equals(JavaUI.ID_CU_EDITOR)) {	
			IFileEditorInput fei;
			fei = (IFileEditorInput) editor.getEditorInput();
			IFile file = fei.getFile();
			return JavaCore.create(file);				
		}
		else if (editor.getEditorSite().getId().equals(JavaUI.ID_CF_EDITOR)){	
			IClassFileEditorInput fei;
			fei = (IClassFileEditorInput) editor.getEditorInput();
			return fei.getClassFile();
		}
		else
			return null;
	}
	
	public IJavaElement getJavaElement(IWorkbenchPart editor) {
		if (editor == null) return null;

		if (editor.getSite().getId().equals(JavaUI.ID_CU_EDITOR)) {				
			IEditorPart ep = (IEditorPart) editor;	
			IFileEditorInput fei = (IFileEditorInput) ep.getEditorInput();
			IFile file = fei.getFile();
			return JavaCore.create(file);
		}
		else if (editor.getSite().getId().equals(JavaUI.ID_CF_EDITOR)) {
			IEditorPart ep = (IEditorPart) editor;	
			IClassFileEditorInput fei = (IClassFileEditorInput) ep.getEditorInput();
			return fei.getClassFile();
		}
		else
			return null;
	}

	public IType getType(IJavaElement javaelement) {
		if (javaelement instanceof IType) {
			return (IType) javaelement;
		}
		else if (javaelement instanceof IMethod) {
			return (IType) javaelement.getAncestor(IJavaElement.TYPE);		
		}
		else if (javaelement instanceof IField) {
			return (IType) javaelement.getAncestor(IJavaElement.TYPE);		
		}
		else
			return null;
	}
	
	public static boolean isTop(IType type) {
		IJavaElement topElement = EditorUtility.getActiveEditorJavaInput();
		
		if (topElement == null)
			return false;
		
		if (type.getTypeRoot().getHandleIdentifier().equals(topElement.getHandleIdentifier()))
			return true;
		else
			return false;
	}
	
}