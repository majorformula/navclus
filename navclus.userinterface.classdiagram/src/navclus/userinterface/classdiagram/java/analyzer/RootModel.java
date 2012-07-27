package navclus.userinterface.classdiagram.java.analyzer;

import java.util.ArrayList;

import navclus.userinterface.classdiagram.PlugIn;
import navclus.userinterface.classdiagram.java.manager.ConnectionNode;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.java.manager.TypeNode;
import navclus.userinterface.classdiagram.utils.FlagRedraw;
import navclus.userinterface.classdiagram.utils.TypeHistory;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;

public class RootModel {

	private RootNode rootNode; 	
	protected TypeModel typemodel;
	protected RelationModel relationmodel;	
	protected RelationAnalyzer relationanalyzer;

	public RootModel(RootNode rootNode) {		
		this.rootNode = rootNode;
		this.typemodel = new TypeModel(rootNode);	
		this.relationanalyzer = new RelationAnalyzer(rootNode);
		this.relationmodel = new RelationModel(rootNode);
	}
		
	public RootNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(RootNode rootNode) {
		this.rootNode = rootNode;
	}
	
	public boolean contain(IType curType) {
		return rootNode.contain(curType);	
	}

	public void cleanUp() {
		rootNode.dispose();
	}
	
	public RelationAnalyzer getRelationModel() {
		return relationanalyzer;
	}

	public void setRelationModel(RelationAnalyzer relationmodel) {
		this.relationanalyzer = relationmodel;
	}
	
	public void addJavaFile(IJavaElement _element) throws JavaModelException {	
		TypeHistory.setCurElement(_element);				
		if (_element instanceof ICompilationUnit) {
			this.openCU((ICompilationUnit) _element);
		} else if (_element instanceof IClassFile) {
			this.openClass((IClassFile) _element);
		} else if (_element instanceof IType) {
			this.createType((IType) _element);
		} else if (_element instanceof IMember) {
			_element = _element.getAncestor(IJavaElement.TYPE);
			this.createType((IType) _element);
		} else {
			MessageDialog.openInformation(PlugIn.getDefaultShell(),
					"Error", 
					"Cannot open this kind of Java Element:" + _element);
		}		
	}
	
	/**
	 * Opens a compilation unit and all the types in it.
	 */
	public void openCU(ICompilationUnit cu)	throws JavaModelException {			
		// not including the embedded class
		IType[] types = cu.getTypes(); 

		for (int i = 0; i < types.length; i++) {
			createType(types[i]);
			break;
		}
	}

	/**
	 * Loads a class into the editor.
	 */
	public void openClass(IClassFile classFile) throws JavaModelException {
		IType type = classFile.getType();		
		createType(type);
	}
	
	public TypeNode createType(final IType curType) {
		if (curType == null) return null;
			
		// return null if the curNode exists in the list & find the position
		if (rootNode.contain(curType))
			return null;
				
		// add the node to list of the root part
		TypeNode typeNode;		
		typeNode = rootNode.addNode(curType);

		// print info.
//		rootNode.printInfo();
		
		// add the connections of the node to the list of the root part
		createConnections(typeNode);
		
		// draw the new nodes & connections 		
//		rootNode.updateAddView();

		return typeNode;
	}
		
	public void createConnections(TypeNode curNode) {			
		ArrayList<TypeNode> nodes = rootNode.getTypeNodes();

		for (TypeNode preNode: nodes) {
			if (preNode.getType().getHandleIdentifier().equals(curNode.getType().getHandleIdentifier()))
				continue;

			try {
				draw_Relationships(preNode, curNode);
			} catch (JavaModelException e) {
				e.printStackTrace();
			}							
		}							
	}
	
	public void createNavigationalRelation(IType type1, IType type2) {
		TypeNode node1 = rootNode.findNode(type1);
		TypeNode node2 = rootNode.findNode(type2);
		
		ConnectionNode connection = rootNode.addNavigationalRelation(node1, node2, "");
		
		// convert the order
		connection.setArrowTip(3); // start_tip;
		
		// add related methods
		FlagRedraw.setSuper(true);								
	}
		
//	/**
//	 * Opens a compilation unit and all the types in it.
//	 */
//	public void closeCU(ICompilationUnit cu)
//	throws JavaModelException {
//		IType[] types = cu.getAllTypes();
//
//		for (int i = 0; i < types.length; i++) {
//			deleteTypewithChildren(types[i]);
//		}
//	}
//
//	/**
//	 * Loads a class into the editor.
//	 */
//	public void closeClass(IClassFile classFile) throws JavaModelException {
//		IType type = classFile.getType();
//		deleteTypewithChildren(type);
//	}
	
	/**
	 * Opens a compilation unit and all the types in it.
	 */
	public void closeCUwoUpdate(ICompilationUnit cu)
	throws JavaModelException {
		IType[] types = cu.getAllTypes();

		for (int i = 0; i < types.length; i++) {
			deleteTypewithChildrenwoUpdate(types[i]);
		}
	}

	/**
	 * Loads a class into the editor.
	 */
	public void closeClasswoUpdate(IClassFile classFile) throws JavaModelException {
		IType type = classFile.getType();
		deleteTypewithChildrenwoUpdate(type);
	}

//	public void deleteTypewithChildren(IType curType) {		
//		if (rootNode.removeNodewithChildren(curType))
//			rootNode.updateDeleteView();
//	}
	
	public void deleteTypewithChildrenwoUpdate(IType curType) {		
		rootNode.removeNodewithChildren(curType);
	}
	
	///////////////////////////from type model 
	////////////////////////// called by PatternPresenter 
	public void addMember(IJavaElement locElement) {
		if (locElement == null) return;

		TypeNode locNode = getTypeNode(locElement);
		if (locNode == null) return;

		if (addMember(locNode, locElement)) {
//			rootNode.synchronizeNodesinView();
		}	
		return;
	}
	
	// finding the node having itypeTop
	// if finding the node, add the javaelement methods
	private TypeNode getTypeNode(IJavaElement locElement) {		
		IType locType = (IType) locElement.getAncestor(IJavaElement.TYPE);			
		
		TypeNode locNode = rootNode.findNode(locType);
		if (locNode == null) {
			locNode = createType(locType);
			
			// drawing?
//			rootNode.synchronizeNodesinView();
		}		
		
		return locNode;	
	}
	

	private boolean addMember(TypeNode locNode, IJavaElement locElement) {		
		if (locElement instanceof IMethod) {
			return locNode.addMethod((IMethod) locElement);
		}
		else if (locElement instanceof IField) {
			return locNode.addField((IField) locElement);
		}
		return false;
	}
	
	
	// null point exception!!!
	public boolean addElement(IType topType, IJavaElement locElement) {

		boolean bExist = false;

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

		boolean bCreateType = false;
		if (locNode == null) {
			locNode = createType(locType); 
//			rootNode.synchronizeNodesinView();
			bCreateType = true;
		}

		if (locNode != null) {
			if (!topType.getHandleIdentifier().equals(locType.getHandleIdentifier())) {
				topNode.embeddedTypes.add(locType);
				TypeHistory.setPreType(locType);
			}

			if (locElement instanceof IMethod) {
				bExist = locNode.addMethod((IMethod) locElement);
			}
			else if (locElement instanceof IField) {
				bExist = locNode.addField((IField) locElement);
			}
			else if (bCreateType)
				return true;
			else
				return false;	
		}
		
		if (bExist) {
//			rootNode.synchronizeNodesinView();
			return true;
		}
		else
			return false;
	}
	
	public void draw_Relationships(TypeNode preNode, TypeNode curNode) throws JavaModelException {
		try {
			
			//----- Inheritance ----- //
			// class - extends: preNode --> curNode
			if (relationanalyzer.doesExtend(preNode, curNode)) {
				relationmodel.drawExtend(curNode, preNode); // Error! salee
				return;
			}
			if (relationanalyzer.doesExtend(curNode, preNode)) {			
				relationmodel.drawExtend(preNode, curNode);
				return;
			}

			// interfaces - implements: preNode --> curNode
			if (relationanalyzer.doesImplement(preNode, curNode)) {
				relationmodel.drawImplement(curNode, preNode);
				return;
			}
						
			if (relationanalyzer.doesImplement(curNode, preNode)) {			
				relationmodel.drawImplement(preNode, curNode);
				return;
			}
						
			// used: preNode --> curNode
			if (relationanalyzer.usedLocalMembers(preNode, curNode))
				relationmodel.drawUses(curNode, preNode);
			if (relationanalyzer.usedLocalMembers(curNode, preNode))
				relationmodel.drawUses(preNode, curNode);			
			
//		// Optional from here: use a class as local variables & methods : preType --> curType
//		relationmodel.draw_usesLocalMemberParts(preNode, curNode);
//		relationmodel.draw_usesLocalMemberParts(curNode, preNode);
//		 for test
//		System.out.println(preNode.getType().getElementName() + " " + curNode.getType().getElementName());

		
//		System.out.println(curNode.getType().getElementName() + " " + preNode.getType().getElementName());


//			// declares other classes: preType --> curType
//			if (relationanalyzer.doesDeclare(preNode, curNode))
//						relationmodel.drawDeclare(preNode, curNode);
//			if (relationanalyzer.doesDeclare(curNode, preNode))
//						relationmodel.drawDeclare(curNode, preNode);
//			
//			// use a class as a variable: preType --> curType
//			if (relationanalyzer.usesVariable(preNode, curNode))
//						relationmodel.drawUseVariable(preNode, curNode);
//			if (relationanalyzer.usesVariable(curNode, preNode))
//						relationmodel.drawUseVariable(curNode, preNode);
//						
//			// Optional from here: use a class as a parameter : preType --> curType
//			if (relationanalyzer.usesParameter(preNode, curNode))
//						relationmodel.drawUseParameter(preNode, curNode);
//			if (relationanalyzer.usesParameter(curNode, preNode))
//						relationmodel.drawUseParameter(curNode, preNode);
//			
//			// Optional from here: use a class as a return type : preType --> curType
//			if (relationanalyzer.usesReturnType(preNode, curNode))
//						relationmodel.drawUseReturn(preNode, curNode);
//			if (relationanalyzer.usesReturnType(curNode, preNode))			
//						relationmodel.drawUseReturn(curNode, preNode);							

//			// check its descendants - Modify more
//			boolean bchild = isDeclared(preNode, curNode);
//
//			if (!bchild) {
////				// Optional from here: use a class as local variables & methods : preType --> curType
////				relationmodel.draw_usesLocalMemberParts(preNode, curNode);
////				relationmodel.draw_usesLocalMemberParts(curNode, preNode);
////				 for test
////				System.out.println(preNode.getType().getElementName() + " " + curNode.getType().getElementName());
//				draw_usesLocalMemberPartswithoutBinding(preNode, curNode);
//				
////				System.out.println(curNode.getType().getElementName() + " " + preNode.getType().getElementName());
//				draw_usesLocalMemberPartswithoutBinding(curNode, preNode);
//			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}		
	}
	
	public void clearModelinView() {
		rootNode.clear();
	}
	
	public void printNodes() {
		rootNode.printNodes();
	}
	
	public void drawNodes() {
		rootNode.drawGraphNodes();
	}
}
