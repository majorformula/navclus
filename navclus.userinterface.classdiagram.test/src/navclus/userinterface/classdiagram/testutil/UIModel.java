package navclus.userinterface.classdiagram.testutil;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.DialogUtil;
import org.eclipse.ui.part.FileEditorInput;

public class UIModel {

	// File Open
	public void openJavaFile(IFile file) {
		//
		// get default editor descriptor
		//
		IEditorRegistry editorRegistry = WorkbenchPlugin.getDefault().getEditorRegistry();
		IEditorDescriptor defaultEditorDescriptor = editorRegistry.getDefaultEditor(file.toString());
		//// || defaultEditorDescriptor.isOpenExternal() is only eclipse 3.x!!!
		if( defaultEditorDescriptor == null){
			defaultEditorDescriptor = editorRegistry.getDefaultEditor("dummy.txt");
		}

		// Open new file in editor
		IWorkbenchWindow dw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		FileEditorInput fileEditorInput = new FileEditorInput(file);
		try {
			IWorkbenchPage page = dw.getActivePage();
			if (page != null)
				//page.openEditor(fileEditorInput,"org.eclipse.ui.DefaultTextEditor");
				page.openEditor(fileEditorInput, defaultEditorDescriptor.getId() );
		} catch (PartInitException e) {
			DialogUtil.openError(dw.getShell(), "Could not open new file",
					e.getMessage(), e);
		}

	}
	
	// File Close
	public void closeJavaFile(IFile file) {
		//
		// get default editor descriptor
		//
		IEditorRegistry editorRegistry = WorkbenchPlugin.getDefault().getEditorRegistry();
		IEditorDescriptor defaultEditorDescriptor = editorRegistry.getDefaultEditor(file.toString());
		//// || defaultEditorDescriptor.isOpenExternal() is only eclipse 3.x!!!
		if( defaultEditorDescriptor == null){
			defaultEditorDescriptor = editorRegistry.getDefaultEditor("dummy.txt");
		}

		// Open new file in editor
		IWorkbenchWindow dw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		FileEditorInput fileEditorInput = new FileEditorInput(file);

		IWorkbenchPage page = dw.getActivePage();
		
		// start!!
		if (page != null) {
			IEditorReference[] editorreferences = page.getEditorReferences();
			
			for (IEditorReference editorreference: editorreferences) {
				try {
					if (fileEditorInput.equals(editorreference.getEditorInput())) {
						page.closeEditor(editorreference.getEditor(true), false);
					}
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}		
}
