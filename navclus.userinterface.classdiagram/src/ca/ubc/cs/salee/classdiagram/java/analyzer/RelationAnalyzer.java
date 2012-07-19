package ca.ubc.cs.salee.classdiagram.java.analyzer;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

import ca.ubc.cs.salee.classdiagram.java.manager.RootNode;
import ca.ubc.cs.salee.classdiagram.java.manager.TypeNode;

public class RelationAnalyzer {
	
	RootNode rootnode;
	
	public RelationAnalyzer(RootNode rootnode) {
		this.rootnode = rootnode;
	}
	
	public boolean doesExtend(TypeNode node1, TypeNode node2)
			throws JavaModelException {

		if ((node1 == null) || (node2 == null))
			return false;

		String superclassName = node1.getType().getSuperclassName();
		if (superclassName == null)
			return false;

		if (superclassName.contains("<")) {
			int index = superclassName.indexOf('<');
			superclassName = superclassName.substring(0, index);
		}

		if ((node2.getType().getElementName().equals(superclassName))
				|| (node2.getType().getFullyQualifiedName()
						.equals(superclassName))) {
			return true;
		}
		
		String[] superinterfaces = node1.getType().getSuperInterfaceNames();
		
		for (String superinterfaceName: superinterfaces){
			if ((node2.getType().getElementName().equals(superinterfaceName))
				|| (node2.getType().getFullyQualifiedName().equals(superinterfaceName))) {
				
				if (node1.getType().isInterface() && node2.getType().isInterface()) {
					return true;
				}				
			}
		}
		
		return false;
	}
	
	public boolean doesImplement(TypeNode node1, TypeNode node2) throws JavaModelException {
		
		if ((node1 == null) || (node2 == null))
			return false;
				
		String[] superinterfaces = node1.getType().getSuperInterfaceNames();				
					
		for (String superinterfaceName: superinterfaces){
			if ((node2.getType().getElementName().equals(superinterfaceName))
				|| (node2.getType().getFullyQualifiedName().equals(superinterfaceName))) {
				
				if (node1.getType().isClass() && node2.getType().isInterface()) {
					return true;
				}
				else if (node1.getType().isEnum() && node2.getType().isInterface()) {
					return true;
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean doesDeclare(TypeNode node1, TypeNode node2) throws JavaModelException {
		
		if ((node1 == null) || (node2 == null))
			return false;
		
		IType[] types = node1.getType().getTypes();				
		for (IType tp: types) {
			if (tp.getHandleIdentifier().equals(node2.getType().getHandleIdentifier())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean usesVariable(TypeNode node1, TypeNode node2) throws JavaModelException {
		if ((node1 == null) || (node2 == null))
			return false;
		
		IField[] fields = node1.getType().getFields();				
		for (IField field: fields) {
			if (Signature.toString(field.getTypeSignature()).equals(node2.getType().getElementName())) {													

				boolean bexist = rootnode.isStructurallyRelated(node1, node2);

				if (!bexist) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean usesParameter(TypeNode node1, TypeNode node2) throws JavaModelException {
		
		if ((node1 == null) || (node2 == null))
			return false;
		
		IMethod[] methods = node1.getType().getMethods();
		for (IMethod method: methods) {
			String[] parameters = method.getParameterTypes();					
			for (String parameter: parameters) {						
				if (Signature.toString(parameter).equals(node2.getType().getElementName())) {

					boolean bexist = rootnode.isStructurallyRelated(node1, node2);

					if (!bexist) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean usesReturnType(TypeNode node1, TypeNode node2) throws JavaModelException {
		
		if ((node1 == null) || (node2 == null))
			return false;
		
		IMethod[] methods = node1.getType().getMethods();
		for (IMethod method: methods) {
			String returntype = method.getReturnType();
			if (Signature.toString(returntype).contains(node2.getType().getElementName())) {

				boolean bexist = rootnode.isStructurallyRelated(node1, node2);

				if (!bexist) {
					return true;
				}				
			}
		}
		return false;
	}
		
	public boolean isDeclared(TypeNode node1, TypeNode node2) throws JavaModelException {

		IType[] types = node1.getType().getTypes();

		if (types.length == 0)
			return false;
		else {
			for (IType tp: types) {
				if (tp.getHandleIdentifier().equals(node2.getType().getHandleIdentifier())) {
					return true;
				}
			}

			for (IType tp: types) {
				if (isDeclared(tp, node2.getType()))
					return true;
			}
		}
		return false;
	}
	
//	public void add_OverridingMethods(TypeNode node1, TypeNode node2) throws JavaModelException {
//		IMethod[] methods1 = node1.getType().getMethods();
//		IMethod[] methods2 = node2.getType().getMethods();
//
//		for (IMethod method1: methods1) {
//			for (IMethod method2: methods2) {
//				if (method1.getElementName().equals(method2.getElementName())) {
////					node1.relMethods.put(method1, node2.getType());
////					node2.relMethods.put(method2, node1.getType());
//				}			
//			}
//		}		
//	}	
	
	public boolean isDeclared(IType type1, IType type2) throws JavaModelException {

		IType[] types = type1.getTypes();

		if (types.length == 0)
			return false;
		else {
			for (IType tp: types) {
				if (tp.getHandleIdentifier().equals(type2.getHandleIdentifier())) {
					return true;
				}
			}

			for (IType tp: types) {
				if (isDeclared(tp, type2))
					return true;
			}
		}
		return false;
	}
	
	// node2 refers to node1 !!!
	public boolean usedLocalMembers(
			final TypeNode node1, 
			final TypeNode node2) throws JavaModelException {
		
		boolean bConnected = false;
		
		IType type1 = node1.getType();
		IType type2 = node2.getType();		

		// type 2's fields/Methods which refer to type 1	
		final Set<IField>  fieldsofType2 = new LinkedHashSet<IField>();
		final Set<IMethod> methodsofType2 =new LinkedHashSet<IMethod>(); 

		// Searching the connection from type 2 to type 1
		SearchPattern pattern = SearchPattern.createPattern(type1, IJavaSearchConstants.REFERENCES);		
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(new IJavaElement[] {type2});
		SearchRequestor requestor = new SearchRequestor() {
			
			@Override
			public void acceptSearchMatch(SearchMatch match)
			throws CoreException {
				Object obj = match.getElement();
				if (obj == null) return;
				
				if (obj instanceof IMethod) {
					IMethod element = (IMethod) obj;
					methodsofType2.add(element);
				}
				else if (obj instanceof IField) {
					IField element = (IField) obj;
					fieldsofType2.add(element);
				}				
			}
		};

		// Start to search...
		SearchEngine searchEngine = new SearchEngine();
		try {
			searchEngine.search(pattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, scope, requestor, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
					
//		for (IMethod method: methodsofType2) {
//			System.out.println(type1.getElementName() + "-- is used by -->" + type2.getElementName() +"." + method.getElementName());
//		}		
//		for (IField field: fieldsofType2) {
//			System.out.println(type1.getElementName() + "-- is used by -->>" + type2.getElementName() +"." + field.getElementName());
//		}
		
		if ((methodsofType2.size() == 0) && (fieldsofType2.size() == 0)) 
			return false;
		else
			return true;
//		
//		// Testing: Analyzing AST
//		ASTParser parser = ASTParser.newParser(AST.JLS3);
//		parser.setKind(ASTParser.K_COMPILATION_UNIT);
//		parser.setResolveBindings(false);
//		
//		ITypeRoot unit = node2.getType().getTypeRoot(); 		
//		if (unit.getSource() == null) return;
//		
//		parser.setSource(unit);
//
////		CompilationUnit cu = JavaPlugin.getDefault().getASTProvider().getAST(unit, true, null);
//		CompilationUnit cu = (CompilationUnit) parser.createAST(null);				
//		cu.accept(
//				new TypePartVisitorwithoutBinding(
//						node1, 
//						node2, 
//						methods2,
//						fields2));			
	}
	
//	public void draw_usesLocalMemberParts(
//	final TypeNode node1, 
//	final TypeNode node2) throws JavaModelException {
//
//if (node1.getCu() == null) { 
//		ASTParser parser = ASTParser.newParser(AST.JLS3);
//		parser.setKind(ASTParser.K_COMPILATION_UNIT);
//		parser.setResolveBindings(true);
//		
//		ITypeRoot unit = node1.getType().getTypeRoot(); 
//		parser.setSource(unit);
//
////		CompilationUnit cu = JavaPlugin.getDefault().getASTProvider().getAST(unit, true, null);
//		CompilationUnit cu = (CompilationUnit) parser.createAST(null);	
//		node1.setCu(cu);				
//		cu.accept(new TypePartVisitor(node1, node2, rootnode));	
//}
//else {
//	CompilationUnit cu = node1.getCu();
//	cu.accept(new TypePartVisitor(node1, node2, rootnode));	
//}					
//}
}
