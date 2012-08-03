/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.classdiagram.java.analyzer;

import java.util.ArrayList;

import navclus.userinterface.classdiagram.java.manager.ConnectionNode;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;


public class TypePartVisitor extends ASTVisitor {

	TypeNode node1; 
	TypeNode node2;
	RootNode rootpart;

	TypePartVisitor (TypeNode node1, TypeNode node2, RootNode rootpart) {
		super();
		this.node1 = node1;
		this.node2 = node2;
		this.rootpart = rootpart;
	}

	@Override
	public boolean visit(SimpleName node) {

		ITypeBinding tbinding = node.resolveTypeBinding();			

		IBinding mbinding = node.resolveBinding();
		if (mbinding instanceof IMethodBinding) {
			tbinding = ((IMethodBinding)mbinding).getDeclaringClass();
		}
		else if (mbinding instanceof IVariableBinding) {
			tbinding = ((IVariableBinding)mbinding).getDeclaringClass();
		}
//		else
//			return true;

		if (tbinding == null) return true;
//		if (mbinding == null) return true;

		// if type 2's methods and fields are found in the type 1
		if (node2.getType().getFullyQualifiedName().equals(tbinding.getQualifiedName())) {
			
			// draw a line between node 1 and node 2
			if (!isConnected(node1, node2)) {
//				ConnectionNode connection = createConnectionPart(node1, node2);
//				connection.setText("refers");
			}

			// add the methods including the methods or fields for node 1
			try {
				IJavaElement element = node1.getType().getTypeRoot().getElementAt(node.getStartPosition());

				if (element instanceof IMethod) {
//					IType parentType = (IType) element.getAncestor(IJavaElement.TYPE);

//					if (node1.getType().getElementName().equals(parentType.getElementName()))
//						node1.relMethods.put((IMethod) element, node2.getType());
				}
				else if (element instanceof IField) {					
//					IType parentType = (IType) element.getAncestor(IJavaElement.TYPE);

//					if (node1.getType().getElementName().equals(parentType.getElementName()))
//						node1.relFields.put((IField) element, node2.getType());
				}	
			} catch (JavaModelException e) {
				e.printStackTrace();
			}

			// add methods or fields for node 2
			if (mbinding instanceof IMethodBinding) {
//				IMethod element = (IMethod) mbinding.getJavaElement();		
//				node2.relMethods.put((IMethod) mbinding.getJavaElement(), node1.getType());
			}
			else if (mbinding instanceof IVariableBinding) {
//				IField element = (IField) mbinding.getJavaElement();
//				node2.relFields.put((IField) element, node1.getType());
			}			
		}		
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {

		ITypeBinding tbinding = node.resolveTypeBinding();
		IMethodBinding mbinding = node.resolveConstructorBinding();
		
		if (tbinding == null) return true;
		if (mbinding == null) return true;
		
		if (node2.getType().getFullyQualifiedName().equals(tbinding.getQualifiedName())) {

			// draw a line between node 1 and node 2
			if (!isConnected(node1, node2)) {
//				ConnectionNode connection = createConnectionPart(node1, node2);
//				connection.setText("refers");
			}
		
			// add the methods including the methods or fields for node 1
			try {
				IJavaElement element = node1.getType().getTypeRoot().getElementAt(node.getStartPosition());
				
//				System.out.println(element.getElementName());

				if (element instanceof IMethod) {
//					IType parentType = (IType) element.getAncestor(IJavaElement.TYPE);

//					if (node2.getType().getElementName().equals(parentType.getElementName()))
//						node1.relMethods.put((IMethod) element, node2.getType());
				}
				else if (element instanceof IField) {					
//					IType parentType = (IType) element.getAncestor(IJavaElement.TYPE);

//					if (node1.getType().getElementName().equals(parentType.getElementName()))
//						node1.relFields.put((IField) element, node2.getType());
				}	
			} catch (JavaModelException e) {
				e.printStackTrace();
			}			
			
			// add constructors for node 2
			if ((mbinding instanceof IMethodBinding)) {
				IMethod method = (IMethod) mbinding.getJavaElement();

				if (method != null) {
//					node2.relMethods.put((IMethod) mbinding.getJavaElement(), node1.getType());
				}
			}			
		}
		return super.visit(node);
	}
	
	public ConnectionNode createConnectionPart(TypeNode node1, TypeNode node2) {
		return rootpart.addStructuralRelation(node1, node2, "");
		
	}
	
	public boolean isConnected(TypeNode node1, TypeNode node2)  {		
		// direct-relationships: do not processing anymore if there is a relationship
		ArrayList<ConnectionNode> connections 
			= (ArrayList<ConnectionNode>) ((ArrayList<ConnectionNode>) rootpart.getStructuralRelations()).clone();
		
//		connections.addAll(TypeModel.rootpart.getConnectionparts());
		
		for (ConnectionNode connection: connections) {
			TypeNode SourceNode = connection.getSourceNode();
			TypeNode TargetNode = connection.getDestinationNode();

			if ((node1.getType().getHandleIdentifier().equals(SourceNode.getType().getHandleIdentifier()) 
					&& node2.getType().getHandleIdentifier().equals(TargetNode.getType().getHandleIdentifier())) ||
					(node2.getType().getHandleIdentifier().equals(SourceNode.getType().getHandleIdentifier()) 
							&& node1.getType().getHandleIdentifier().equals(TargetNode.getType().getHandleIdentifier()))) {
//				if (!connection.getLineColor().equals(ColorConstants.white))
					return true;
			}
		}
		return false;
	}
	
	
}
