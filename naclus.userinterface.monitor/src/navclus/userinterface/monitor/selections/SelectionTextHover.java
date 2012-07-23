/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package navclus.userinterface.monitor.selections;

import navclus.userinterface.monitor.Plugin;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.text.java.hover.JavaSourceHover;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;

public class SelectionTextHover extends JavaSourceHover {

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		IJavaElement[] result = getJavaElementsAt(textViewer, hoverRegion);
		if (result == null || result.length == 0 || result.length > 1) {
			return null;
		}

		IJavaElement curr = result[0];
		if (curr != null) {
			System.out.println("selectionChanged3: " + curr.getElementName());
			Plugin.getDefault().getSelectionKeeper().addSelection(curr);
		}

		return super.getHoverInfo(textViewer, hoverRegion);
	}
}
