package navclus.userinterface.classdiagram.java.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IType;

public class TypeNodeList {

	private ArrayList<TypeNode> typenodes = null;
	private ArrayList<TypeNode> nodetrashes = null;
	
	public TypeNodeList() {
		this.typenodes = new ArrayList<TypeNode>();
		this.nodetrashes = new ArrayList<TypeNode>();
	}
	
	public ArrayList<TypeNode> getTypeNodes() {
		return typenodes;
	}

	public ArrayList<TypeNode> getTrashNodes() {
		return nodetrashes;
	}
	
	public int getSize() {
		return typenodes.size();
	}
	
	public boolean contain(IType curType) {				
		for (TypeNode preNode: typenodes) {
			if (preNode.getType().getHandleIdentifier().equals(curType.getHandleIdentifier())) {
				return true;				
			}						
		}
		return false;
	}
	
	public TypeNode findNode(IType curType) {				
		for (TypeNode preNode: typenodes) {
			if (preNode.getType().getHandleIdentifier().equals(curType.getHandleIdentifier())) {
				return preNode;				
			}						
		}
		return null;
	}

	public TypeNode addNode(IType curType) {
		TypeNode nodepart = new TypeNode(curType);		
		this.typenodes.add(nodepart);

		return nodepart;
	}

	public TypeNode addNode(int index, IType curType) {
		TypeNode nodepart = new TypeNode(curType);		
		this.typenodes.add(index, nodepart);

		return nodepart;
	}
	
	public boolean removeNode(TypeNode nodepart) {
		if (nodepart == null) return false;

		this.typenodes.remove(nodepart);
		this.nodetrashes.add(nodepart);
		return true;
	}

	public void dispose() {
		for (TypeNode typeNode: typenodes) {
			typeNode.clear();
		}
		this.typenodes = null; 
		this.nodetrashes.clear(); 
		this.nodetrashes = null;
	}
	
	public void clear() {
		this.typenodes.clear();
		this.nodetrashes.clear();
	}
}
