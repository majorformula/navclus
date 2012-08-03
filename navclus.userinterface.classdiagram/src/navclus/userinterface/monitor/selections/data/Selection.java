/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.monitor.selections.data;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

public class Selection {

	private IJavaElement element;

	public Selection(IJavaElement element) {
		this.element = element;
	}

	public IJavaElement getElement() {
		return element;
	}

	public void setElement(IJavaElement element) {
		this.element = element;
	}

	public void print() {
		if (element == null) {
			return;
		}

		switch (element.getElementType()) {
		case (IJavaElement.TYPE):
			System.out.print("(" + element.getElementName() + "#, ");
			break;
		case (IJavaElement.METHOD):

			IType type = ((IMethod) element).getDeclaringType();
			if (type != null) {
				System.out.print("(" + type.getElementName() + ".");
				System.out.print(element.getElementName() + "(" + ((IMethod) element).getNumberOfParameters() + "), ");
			} else {
				System.out.print("(" + element.getElementName() + "(" + ((IMethod) element).getNumberOfParameters()
						+ "), ");
			}
			break;
		case (IJavaElement.FIELD):
			type = ((IField) element).getDeclaringType();
			if (type != null) {
				System.out.print("(" + type.getElementName() + ".");
				System.out.print(element.getElementName() + ", ");
			} else {
				System.out.print("(" + element.getElementName() + ", ");
			}
			break;
		default:
			System.out.print("(" + element.getElementName() + ", ");
			break;
		}

		System.out.print(")");
	}

}
