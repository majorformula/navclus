package navclus.userinterface.classdiagram.listeners;
/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

//import java.util.ArrayList;
//
//import navclus.userinterface.classdiagram.NavClusView;
//import navclus.userinterface.classdiagram.java.analyzer.RelationModel;
//import navclus.userinterface.classdiagram.java.analyzer.TypeModel;
//
//import org.eclipse.jdt.core.ElementChangedEvent;
//import org.eclipse.jdt.core.IElementChangedListener;
//import org.eclipse.jdt.core.IField;
//import org.eclipse.jdt.core.IJavaElement;
//import org.eclipse.jdt.core.IJavaElementDelta;
//import org.eclipse.jdt.core.IMethod;
//import org.eclipse.jdt.core.IType;
//import org.eclipse.jdt.core.JavaModelException;
//import org.eclipse.jdt.core.Signature;
//import org.eclipse.jface.viewers.Viewer;
//import org.eclipse.swt.widgets.Display;
//
//
//public class JavaChangeListener implements IElementChangedListener {
//
//	NavClusView viewer;	
//	TypeModel typemodel;	
//
//	public JavaChangeListener(NavClusView viewer) {
//		super();
//		this.viewer = viewer;	
//		this.typemodel = viewer.getTypemodel(); // error: check the viewer
//	}
//
//	public void elementChanged(ElementChangedEvent event) {
//		if (event.getType() != IJavaElementDelta.CHANGED) return;
//
//		IJavaElementDelta delta = event.getDelta();
//		if (delta == null) return;
//
//		if (isPossibleStructuralChange(delta.getFlags()))
//			return;		
//
//		hasChanged(delta);		
//		updateView();
//	}
//	
//	private boolean hasChanged(IJavaElementDelta delta) {
//		IJavaElement elem = delta.getElement();
//		
//		switch (elem.getElementType()) {
//		case IJavaElement.JAVA_MODEL: // 1
//		case IJavaElement.JAVA_PROJECT: // 2
//		case IJavaElement.PACKAGE_FRAGMENT_ROOT: // 3 
//		case IJavaElement.PACKAGE_FRAGMENT: // 4
//			return false; 
//		case IJavaElement.COMPILATION_UNIT: // 5
//			return processChildrenDelta(delta.getAffectedChildren());
//		case IJavaElement.TYPE: // 7
//			if ((delta.getFlags() & IJavaElementDelta.F_SUPER_TYPES) != 0) {			
//				IType curType = (IType) elem;	
//
//				RelationModel relationmodel = typemodel.getRelationModel();
//				relationmodel.removeSuperPart(curType);
//				drawSuperType(curType);																
//			} 
//			return processChildrenDelta(delta.getAffectedChildren()); // inner types
//		case IJavaElement.FIELD: // 8
//			IField curField = (IField) elem;
//			IType curTypeofField = (IType) curField.getAncestor(IJavaElement.TYPE);
//			
////			System.out.println("Field: " + elem.getElementName());
////			System.out.println(delta.getKind());
//									
//			if (delta.getKind() == IJavaElementDelta.ADDED ) {
//				boolean bUpdate = typemodel.addElement(curTypeofField, curField);
//				drawFieldTypePart(curTypeofField);		
//			}
//			else if (delta.getKind() == IJavaElementDelta.REMOVED ) {
//				boolean bUpdate = typemodel.removeElement(curTypeofField, curField);
//				
//				NodePart curPart = typemodel.findNode(curTypeofField);
//				if (curPart != null) {
//					RelationModel relationmodel = typemodel.getRelationModel();
//					relationmodel.removeConnectionPart(curTypeofField, "uses as variables");
//				}				
//			}
////			printFlags(delta);			
//			return processChildrenDelta(delta.getAffectedChildren()); // inner types
//		case IJavaElement.METHOD: // 9 add & remove
//			IMethod curMethod = (IMethod) elem;
//			IType curTypeofMethod = (IType) curMethod.getAncestor(IJavaElement.TYPE);			
//			
////			System.out.println("Method: " + elem.getElementName());
////			System.out.println(delta.getKind());
//						
//			if (delta.getKind() == IJavaElementDelta.ADDED ) {
//				boolean bUpdate = typemodel.addElement(curTypeofMethod, curMethod);															
//				drawParameterTypePart(curTypeofMethod);					
//				drawReturnTypePart(curTypeofMethod);									
//			}
//			else if (delta.getKind() == IJavaElementDelta.REMOVED ) {
//				boolean bUpdate = typemodel.removeElement(curTypeofMethod, curMethod);
//				
//				NodePart curPart = typemodel.findNode(curTypeofMethod);
//				if (curPart != null) {
//					RelationModel relationmodel = typemodel.getRelationModel();
//					relationmodel.removeConnectionPart(curTypeofMethod, "uses as parameters");
//					relationmodel.removeConnectionPart(curTypeofMethod, "uses as return types");
//				}
//			}						
////			printFlags(delta);			
//			return processChildrenDelta(delta.getAffectedChildren()); // inner types			
//		default: // imports...
//			return true;
//		}
//	}
//
//	private static boolean isPossibleStructuralChange(int flags)	{
//		if ((flags & IJavaElementDelta.F_CONTENT) != 0)
//			if ((flags & IJavaElementDelta.F_FINE_GRAINED) == 0)
//				return true;
//
//		return false;
//	}
//
//	
//	private boolean processChildrenDelta(IJavaElementDelta[] children) {
//		// sort:		
//		IJavaElementDelta temp;
//		for (int i= 0; i < children.length; i++) {
//			for (int j=0; j < children.length - i -1; j++) {
//				if (children[j].getKind() < children[j+1].getKind()) {
//					temp = children[j];
//					children[j] = children[j+1];
//					children[j+1] = temp;
//				}
//			}
//		}				
//		
//		// processing children
//		for (int i= 0; i < children.length; i++) {
//			IJavaElement elem = children[i].getElement();
//			if (elem == null) continue;
////			System.out.println("---" + elem.getElementName() + ": " + children[i].getKind());
//			if (hasChanged(children[i]))
//				return true;
//		}
//		return false;
//	}
//
//	public void updateView() {	
//		
//		if (Display.getCurrent() != null) {
//			(new RedrawActionOnlyConnection(viewer)).run();
//		}
//		else {
//			Display.getDefault().asyncExec(new Runnable() {
//				public void run() {					
//					(new RedrawActionOnlyConnection(viewer)).run();
//				}
//			});
//		}
//	}
//	
//	private void drawSuperType(IType curType) {
//		NodePart curPart = typemodel.findNode(curType);
//		if (curPart == null) return;
//		
//		RootPart rootpart = typemodel.getRootpart();		
//		RelationModel relationmodel = typemodel.getRelationModel();
//
//		ArrayList<NodePart> nodeparts 
//		= (ArrayList<NodePart>) ((ArrayList<NodePart>) rootpart.getNodeparts()).clone();
//
//		for (NodePart prePart: nodeparts) {
//			if (prePart.getType().getHandleIdentifier().equals(curType.getHandleIdentifier()))
//				continue;
//
//			try {
//				if (IsExtendParts(curType, prePart.getType()))
//					relationmodel.draw_ExtendParts(curPart, prePart);
//				if (IsImplementParts(curType, prePart.getType()))
//					relationmodel.draw_ImplementParts(curPart, prePart);
//
//			} catch (JavaModelException e) {
//				e.printStackTrace();
//			}							
//		}
//	}
//	
//	private void drawFieldTypePart(IType curType) {
//		NodePart curPart = typemodel.findNode(curType);
//		if (curPart == null) return;
//		
//		RootPart rootpart = typemodel.getRootpart();
//		RelationModel relationmodel = typemodel.getRelationModel();
//
//		ArrayList<NodePart> nodeparts 
//		= (ArrayList<NodePart>) ((ArrayList<NodePart>) rootpart.getNodeparts()).clone();
//
//		for (NodePart prePart: nodeparts) {
//			if (prePart.getType().getHandleIdentifier().equals(curType.getHandleIdentifier()))
//				continue;
//			try {
//				if (IsFieldParts(curType, prePart.getType()))
//					relationmodel.draw_usesVariableParts(curPart, prePart);
//			} catch (JavaModelException e) {
//				e.printStackTrace();
//			}							
//		}
//		return;
//	}
//	
//	private void drawParameterTypePart(IType curType) {	
//		NodePart curPart = typemodel.findNode(curType);
//		if (curPart == null) return;
//		
//		RootPart rootpart = typemodel.getRootpart();
//		RelationModel relationmodel = typemodel.getRelationModel();
//
//		ArrayList<NodePart> nodeparts 
//		= (ArrayList<NodePart>) ((ArrayList<NodePart>) rootpart.getNodeparts()).clone();
//
//		for (NodePart prePart: nodeparts) {
//			if (prePart.getType().getHandleIdentifier().equals(curType.getHandleIdentifier()))
//				continue;
//			try {
//				if (IsParameterParts(curType, prePart.getType()))
//					relationmodel.draw_usesParameterParts(curPart, prePart); 
//			} catch (JavaModelException e) {
//				e.printStackTrace();
//			}							
//		}
//		return;
//	}
//	
//	private void drawReturnTypePart(IType curType) {
//		NodePart curPart = typemodel.findNode(curType);
//		if (curPart == null) return;
//		
//		RootPart rootpart = typemodel.getRootpart();
//		RelationModel relationmodel = typemodel.getRelationModel();
//
//		ArrayList<NodePart> nodeparts 
//		= (ArrayList<NodePart>) ((ArrayList<NodePart>) rootpart.getNodeparts()).clone();
//
//		for (NodePart prePart: nodeparts) {
//			if (prePart.getType().getHandleIdentifier().equals(curType.getHandleIdentifier()))
//				continue;
//			try {
//				if (IsReturnTypeParts(curType, prePart.getType()))
//					relationmodel.draw_usesReturnTypeParts(curPart, prePart);
//			} catch (JavaModelException e) {
//				e.printStackTrace();
//			}							
//		}
//	}
//
//	public boolean IsFieldParts(IType type1, IType type2) throws JavaModelException {
//		
//		if (type1 == null) return false;
//		if (type2 == null) return false;
//		
//		IField[] fields = type1.getFields();				
//		for (IField field: fields) {
//			if (Signature.toString(field.getTypeSignature()).equals(type2.getElementName())) {													
//				return true;
//			}
//		}
//		
//		return false;
//	}
//	
//	public boolean IsExtendParts(IType type1, IType type2) throws JavaModelException {
//
//		if (type1 == null) return false;
//		if (type2 == null) return false;
//
//		String superclassName = type1.getSuperclassName();
//
//		if (superclassName != null) {
//
//			if (superclassName.contains("<")) {
//				int index = superclassName.indexOf('<');
//				superclassName = superclassName.substring(0, index);
//			}	
//
//			if ((type2.getElementName().equals(superclassName)) 
//					|| (type2.getFullyQualifiedName().equals(superclassName))) {				
//				return true;				
//			}
//		}
//		return false;
//	}
//
//	public boolean IsImplementParts(IType type1, IType type2) throws JavaModelException {
//
//		if (type1 == null) return false;
//		if (type2 == null) return false;
//
//		String[] superinterfaces = type1.getSuperInterfaceNames();				
//
//		for (String superinterfaceName: superinterfaces){
//			if ((type2.getElementName().equals(superinterfaceName))
//					|| (type2.getFullyQualifiedName().equals(superinterfaceName))) {
//
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean IsParameterParts(IType type1, IType type2) throws JavaModelException {
//		
//		if (type1 == null) return false;
//		if (type2 == null) return false;		
//		
//		IMethod[] methods = type1.getMethods();
//		for (IMethod method: methods) {
//			String[] parameters = method.getParameterTypes();					
//			for (String parameter: parameters) {						
//				if (Signature.toString(parameter).equals(type2.getElementName())) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	
//	public boolean IsReturnTypeParts(IType type1, IType type2) throws JavaModelException {
//		
//		if (type1 == null) return false;
//		if (type2 == null) return false;
//		
//		IMethod[] methods = type1.getMethods();
//		for (IMethod method: methods) {
//			String returntype = method.getReturnType();
//			if (Signature.toString(returntype).contains(type2.getElementName())) {
//				return true;
//			}
//		}
//		return false;
//	}
//		
//	void printFlags(IJavaElementDelta delta) {
//
//		if ((delta.getFlags() & IJavaElementDelta.F_ADDED_TO_CLASSPATH) != 0) {
//			System.out.println(" was F_ADDED_TO_CLASSPATH");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_ARCHIVE_CONTENT_CHANGED) != 0) {
//			System.out.println(" was F_ARCHIVE_CONTENT_CHANGED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_ARCHIVE_CONTENT_CHANGED) != 0) {
//			System.out.println(" was F_ARCHIVE_CONTENT_CHANGED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_AST_AFFECTED) != 0) {
//			System.out.println(" was F_AST_AFFECTED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_CATEGORIES) != 0) {
//			System.out.println(" was F_CATEGORIES");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_CHILDREN) != 0) {
//			System.out.println(" was F_CHILDREN");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_CLASSPATH_CHANGED) != 0) {
//			System.out.println(" was F_CLASSPATH_CHANGED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_CLOSED) != 0) {
//			System.out.println(" was F_CLOSED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_CONTENT) != 0) {
//			System.out.println(" was F_CONTENT");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_FINE_GRAINED) != 0) {
//			System.out.println(" was F_FINE_GRAINED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_MODIFIERS) != 0) {
//			System.out.println(" was F_MODIFIERS");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_MOVED_FROM) != 0) {
//			System.out.println(" was F_MOVED_FROM");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_MOVED_TO) != 0) {
//			System.out.println(" was F_MOVED_TO");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_OPENED) != 0) {
//			System.out.println(" was F_OPENED");
//		}      
//		if ((delta.getFlags() & IJavaElementDelta.F_PRIMARY_RESOURCE) != 0) {
//			System.out.println(" was F_PRIMARY_RESOURCE");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_PRIMARY_WORKING_COPY) != 0) {
//			System.out.println(" was F_PRIMARY_WORKING_COPY");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_REMOVED_FROM_CLASSPATH) != 0) {
//			System.out.println(" was F_REMOVED_FROM_CLASSPATH");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_REORDER) != 0) {
//			System.out.println(" was F_REORDER");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_SOURCEATTACHED) != 0) {
//			System.out.println(" was F_SOURCEATTACHED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_SOURCEDETACHED) != 0) {
//			System.out.println(" was F_SOURCEDETACHED");
//		}
//		if ((delta.getFlags() & IJavaElementDelta.F_SUPER_TYPES) != 0) {
//			System.out.println(" was F_SUPER_TYPES");
//		}   	    	
//	}
//
//}
