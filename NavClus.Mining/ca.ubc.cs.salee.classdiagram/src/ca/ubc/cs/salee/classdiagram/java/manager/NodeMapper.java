package ca.ubc.cs.salee.classdiagram.java.manager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphConnection;

import ca.ubc.cs.salee.classdiagram.Viewer;
import ca.ubc.cs.salee.classdiagram.classfigure.ClassFigureCreator;
import ca.ubc.cs.salee.classdiagram.classfigure.UMLNode;

public class NodeMapper {

	protected Viewer viewer; 
	public HashMap<TypeNode, UMLNode> nodetable; 	// salee
	
	
	public NodeMapper(Viewer viewer) {
		this.viewer = viewer;
		this.nodetable = new LinkedHashMap<TypeNode, UMLNode>(); // salee	
	}

	public UMLNode get(TypeNode typenode) {
		return nodetable.get(typenode);		
	}
	
	public TypeNode get(UMLNode umlnode) {
		Set<TypeNode> typenodeset = nodetable.keySet();
		
		for (TypeNode typenode: typenodeset) {
			UMLNode node = this.get(typenode);
			if (node == null) continue;
		
			if (node.equals(umlnode)) {
				System.err.println("test:TypeNode.get");
				return typenode;
			}
		}
		
		return null;		
	}
	
	public boolean remove(TypeNode typenode) {
		System.out.println("remove:" + typenode.getType().getElementName());
		
		UMLNode node = this.get(typenode);		
		if (node == null) return false;
		
		nodetable.remove(typenode);
		node.dispose();
		return true;
	}
	
	public void removeAll() {		
		Set<TypeNode> typenodeset = nodetable.keySet();
		
		for (TypeNode typenode: typenodeset) {
			UMLNode node = this.get(typenode);		
			if (node == null) continue;
			node.dispose();		
		}
		nodetable.clear();
	}
		
	public Point removeAtPosition(TypeNode typenode) {
		UMLNode node = this.get(typenode);
		Point curPoint = node.getLocation();		
		if (node == null) return null;
		
		nodetable.remove(typenode);
		node.dispose();
		return curPoint;
	}
	
	public UMLNode create(TypeNode typenode) {	
		UMLNode curNode;
		
		curNode = new UMLNode(viewer.getG(), SWT.NONE, 
		 		  (new ClassFigureCreator()).createClassFigure(typenode));					
		curNode.setText((typenode.getType().getHandleIdentifier()));
									
		nodetable.put(typenode, curNode);
		
		return curNode;
	}

	public UMLNode create(TypeNode typenode, Point curPoint) {
		UMLNode curNode;
		
		curNode = new UMLNode(viewer.getG(), SWT.NONE, 
		 		  (new ClassFigureCreator()).createClassFigure(typenode));					
		curNode.setText((typenode.getType().getHandleIdentifier()));
		curNode.setLocation(curPoint.x, curPoint.y);
		
		nodetable.put(typenode, curNode);
		
		return curNode;
	}
	
	public void clear() {
		nodetable.clear();
	}
}