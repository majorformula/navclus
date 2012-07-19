package ca.ubc.cs.salee.classdiagram.java.analyzer;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import ca.ubc.cs.salee.classdiagram.java.manager.TypeNode;

public class TypePartVisitorwithoutBinding extends ASTVisitor {
	
	TypeNode nodePart1; 
	TypeNode nodePart2;
	Set<IMethod> methods2;
	Set<IField> fields2;
	String typeName;

	TypePartVisitorwithoutBinding (TypeNode nodePart1, 
								   TypeNode nodePart2, 
								   Set<IMethod> methods2, 
								   Set<IField>  fields2) {
		super();
		this.nodePart1 = nodePart1;		
		this.nodePart2 = nodePart2;
		this.methods2 = methods2;
		this.fields2 = fields2;
		
		if (nodePart2.getType() == null)
			this.typeName = null;
		else
			this.typeName = nodePart2.getType().getElementName();
	}
	
//	@Override
//	public boolean visit(FieldDeclaration node) {
//		
//		System.out.println("FieldDeclaration:" + node.getType());
//		System.out.println("FieldDeclaration:" + node..toString());
//		
//		return super.visit(node);
//	}
	
	@Override
	public boolean visit(PackageDeclaration node) {
		return false;
	}
	
	@Override
	public boolean visit(ImportDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		if (node.getName() == null) return super.visit(node);
		if (node.getName().getIdentifier() == null) return super.visit(node);
		
		if (node.getName().getIdentifier().equals(typeName)) 
			return true;
		else
			return false;
	}
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
//		System.out.println("ClassInstanceCreation");
		try {
			if (nodePart1.getType() == null) return super.visit(node);
			
			IMethod[] methods = nodePart1.getType().getMethods();
			if (methods.length <= 0) return super.visit(node);

			for (IMethod method: methods) {
				if (method.getElementName() == null) continue;
				
//				if (method.getElementName().equals(node.getType().toString()))
//					nodePart1.relMethods.put(method, nodePart2.getType());
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldAccess node) {
//		System.out.println("FieldAccess");
		try {
			if (nodePart1.getType() == null) return super.visit(node);			
			if (node.getName() == null)  return super.visit(node);
			
			IField[] fields = nodePart1.getType().getFields();
			if (fields.length <= 0) return super.visit(node);
			
			for (IField field: fields) {
				if (field.getElementName() == null) continue;				
				if (field.getElementName().equals(node.getName().getIdentifier())) {
//						nodePart1.relFields.put(field, nodePart2.getType());
//						System.out.println("FieldAccess" + node.getName());
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

//	@Override
//	public boolean visit(MethodDeclaration node) {
//		return super.visit(node);
//////		System.out.println("MethodDeclaration");
////		if (node.getName() == null) return super.visit(node);
////		if (node.getName().getIdentifier() == null) return super.visit(node);		
////		
////		for (IMethod method: methods2) {
//////			System.out.println("method def#: " + method.getElementName());
////			if (node.getName().getIdentifier().equals(method.getElementName())) {
////				return true;
////			}
////		}
////		return false;		
//	}

	@Override
	public boolean visit(MethodInvocation node) {		
//		System.out.println("MethodInvocation:" + node.getName() );	
		try {
			if (node.getName() == null) return super.visit(node);
			if (node.getName().getIdentifier() == null) return super.visit(node);				
			if (nodePart1.getType() == null) return super.visit(node);
						
			IMethod[] methods = nodePart1.getType().getMethods();
			if (methods.length <= 0) return super.visit(node);
			
			for (IMethod method: methods) {
				if (method.getElementName() == null) continue;
				
				if (method.getElementName().equals(node.getName().getIdentifier())) {
					List arguments = node.arguments();
					String[] parameters = method.getParameterTypes();			
					
					if (arguments.size() == parameters.length) {
//						nodePart1.relMethods.put(method, nodePart2.getType());
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleName node) {
//		System.out.println("SimpleName1: " + node.getFullyQualifiedName() + node.getStartPosition());	
		try {
			if (nodePart1.getType() == null) return super.visit(node);			
			if (node.getFullyQualifiedName() == null) return super.visit(node);

			// fields of nodePart1
			IField[] fields = nodePart1.getType().getFields();
			for (IField field: fields) {
				int iFlag = field.getFlags();

				if (Flags.isStatic(iFlag))
					if (node.getFullyQualifiedName().equals(field.getElementName())) {
//						nodePart1.relFields.put(field, nodePart2.getType());
//						System.out.println("SimpleName2: " + node.getFullyQualifiedName());
					}
			}
			
			// "rel_fields of nodePart2
			fields = fields2.toArray(fields);
			if (fields.length <= 0) return super.visit(node);

			for (IField field: fields) {
				if (field == null) return super.visit(node);;
				if (node.getFullyQualifiedName().equals(field.getElementName())) {
//					System.out.println(node.getStartPosition());
					
					IJavaElement locElement = ((ITypeRoot) nodePart2.getType().getTypeRoot()).getElementAt(node.getStartPosition());
					
//					if (locElement instanceof IMethod)
//						nodePart2.relMethods.put((IMethod) locElement, nodePart1.getType());
				}
			}			
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(Javadoc node) {
		return false;
	}
		
}
