/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.classdiagram.utils;

import navclus.userinterface.classdiagram.PlugIn;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;


public class TypeHistory {
	
	private static TypeHistory typehistory;
	
	public static TypeHistory getPreviousType() {
		if (typehistory == null) {
			typehistory = new TypeHistory();
		}
		return typehistory;
	}
	
	private static IJavaElement preElement = null;
	private static IType preType = null;
	private static IJavaElement curElement = null;
	private static IType curType = null;
		
	public static IType getPreType() {
		return preType;
	}

	public static void setPreType(IType preType) {		
		TypeHistory.preType = preType;
	}
		
	public static void setPreElement(IJavaElement preElement) {	
		if (preElement == null) return;
		
		TypeHistory.preElement = preElement;

		if (preElement instanceof ICompilationUnit) {
			IType[] types;	
			
			try {
				types = ((ICompilationUnit) preElement).getTypes();
				
				if (types.length > 0)
					setPreType(types[0]);
				else
					return;
				
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
			return;
		} 
		else if (preElement instanceof IClassFile) {
			setPreType(((IClassFile) preElement).getType());	
		} 
		else if (preElement instanceof IType) {
			setPreType((IType) preElement);
		} 
		else if (preElement instanceof IMember) {
			setPreType((IType) preElement.getAncestor(IJavaElement.TYPE));
		} 
		else {
			MessageDialog.openInformation(PlugIn.getDefaultShell(),
					"Error", 
					"Cannot open this kind of Java Element:" + preElement);
		}			
	}

	public static IJavaElement getPreElement() {
		return (IJavaElement) TypeHistory.preElement;
	}

	public static IJavaElement getCurElement() {
		return curElement;
	}

	public static void setCurElement(IJavaElement curElement) {	
		if (curElement == null) return;
		
		TypeHistory.curElement = curElement;

		if (curElement instanceof ICompilationUnit) {
			IType[] types;	
			
			try {
				types = ((ICompilationUnit) curElement).getTypes();
				
				if (types.length > 0)
					setCurType(types[0]);
				else
					return;
				
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
			return;
		} 
		else if (curElement instanceof IClassFile) {
			setCurType(((IClassFile) curElement).getType());	
		} 
		else if (curElement instanceof IType) {
			setCurType((IType) curElement);
		} 
		else if (curElement instanceof IMember) {
			setCurType((IType) curElement.getAncestor(IJavaElement.TYPE));
		} 
		else {
			MessageDialog.openInformation(PlugIn.getDefaultShell(),
					"Error", 
					"Cannot open this kind of Java Element:" + curElement);
		}		
	}

	public static IType getCurType() {
		return curType;
	}

	public static void setCurType(IType curType) {
		TypeHistory.curType = curType;
	}

	public void printInfo() {
		if (TypeHistory.getCurElement() != null)
			System.out.println(TypeHistory.getCurElement().getElementName());
		if (TypeHistory.getPreElement() != null)
			System.out.println(TypeHistory.getPreElement().getElementName());
	}
}
