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

import org.eclipse.jdt.core.ICodeAssist;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import navclus.userinterface.*;
import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.actions.RedrawAction;
import navclus.userinterface.classdiagram.actions.RedrawActionwoLayout;
//import navclus.userinterface.classdiagram.actions.RedrawActionwoLayout;
import navclus.userinterface.classdiagram.java.analyzer.RootModel;
import navclus.userinterface.classdiagram.utils.FlagRedraw;
import navclus.userinterface.classdiagram.utils.JavaEditorUtil;


public class JavaEditorSelectionListener implements ISelectionListener {

	public void selectionChanged(IWorkbenchPart part,
			ISelection selection) {
				
		if (FlagRedraw.isDone()) {
			FlagRedraw.setDone(false);
			return;
		}
		
		boolean bUpdate = false;

		IJavaElement topElement = JavaEditorUtil.getJavaElement(part);		
		if (topElement == null) return;
		
		if (selection instanceof TextSelection) {
			ITextSelection selectedtext = (ITextSelection) selection;								
			if (selectedtext.getStartLine() < 1)
				return;
			
			
			IType topType = ((ITypeRoot) topElement).findPrimaryType();
			try {
				// do not work if the types of being deleted and created are the same & the type will not be permanent.
				IJavaElement locElement = ((ITypeRoot) topElement).getElementAt(selectedtext.getOffset());
				IJavaElement[] javaelements = ((ICodeAssist) topElement).codeSelect(selectedtext.getOffset(), selectedtext.getLength());

				if (locElement != null)
					System.out.println("selectionChanged: " + locElement.getElementName());
				
				// show the elements in a class figure
				bUpdate = NavClusView.getDefault().getRootModel().addElement(topType, locElement);
				NavClusView.getDefault().getSelectionKeeper().addSelection(locElement);
				(new RedrawAction()).run();
//				System.out.println("loc Element is: " + locElement.getElementName());

				if (bUpdate) {
//					System.out.println("redraw when it is selected");
//					(new RedrawActionwoLayout(viewer)).run();
				}

			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}

}
