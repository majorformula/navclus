/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

package navclus.userinterface.classdiagram.java.analyzer;

import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;


public class TypeModel  {
	
	RootNode rootNode;

	public TypeModel(RootNode rootpart) {
		this.rootNode = rootpart;
	}

	public boolean contain(IType type) {		
		if (rootNode.contain(type)) {
			return true;			
		}
		else
			return false;
	}
	
	// null point exception!!!
	public boolean removeElement(IType topType, IJavaElement locElement) {
		if (topType == null) return false;
		if (locElement == null) return false;
		if (topType.getHandleIdentifier().equals(locElement.getHandleIdentifier()))
			return false;

		// finding the node having itypeTop
		// if finding the node, add the javaelement methods
		IType locType = (IType) locElement.getAncestor(IJavaElement.TYPE);	
		TypeNode topNode = rootNode.findNode(topType);
		TypeNode locNode = rootNode.findNode(locType);
		if (topNode == null) return false;
		if (locNode == null) return false;		
		
		if (IsRemoved(locNode, locElement)) {
//			rootNode.synchronizeNodesinView();
			return true;
		}
		else
			return false;
	}
	
	private boolean IsRemoved(TypeNode locNode, IJavaElement locElement) {
		if (locElement instanceof IMethod) {
			return locNode.removeMethod((IMethod) locElement);
		}
		else if (locElement instanceof IField) {
			return locNode.removeField((IField) locElement);
		}
		else if (locElement instanceof IType) {
			return locNode.removeType((IType) locElement);
		}
		
		
		return false;		
	}
		
}
