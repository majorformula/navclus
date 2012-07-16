package ca.ubc.cs.salee.classdiagram.java.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IType;

import ca.ubc.cs.salee.classdiagram.java.manager.ConnectionNode;
import ca.ubc.cs.salee.classdiagram.java.manager.RootNode;
import ca.ubc.cs.salee.classdiagram.java.manager.TypeNode;
import ca.ubc.cs.salee.classdiagram.utils.FlagRedraw;

public class RelationModel {
	
	private RootNode rootNode; 	
	
	public RelationModel(RootNode rootnode) {
		this.rootNode = rootnode;
	}

	// belowNode extends aboveNode
	void drawExtend(TypeNode parentNode, TypeNode childNode) {
		ConnectionNode connection = createConnectionPart(childNode, parentNode);
		
		// convert the order
		connection.setArrowTip(3); // start_tip;
		if (connection.getTag().equals(""))
			connection.setTag("extends");
		
		// add related methods
		FlagRedraw.setSuper(true);
	}
	
	// belowNode implements aboveNode
	void drawImplement(TypeNode parentNode, TypeNode childNode) {
		ConnectionNode connection = createConnectionPart(childNode, parentNode);
		
//		connection.setLineStyle(Graphics.LINE_DASH);
		connection.setArrowTip(3); // start_tip;
		if (connection.getTag().equals(""))
			connection.setTag("implements");
		
		FlagRedraw.setSuper(true);
	}
	
	// aboveNode uses belowNode
	void drawUses(TypeNode callerNode, TypeNode calledNode) {
		ConnectionNode connection = createConnectionPart(callerNode, calledNode);
		
//		connection.setLineStyle(Graphics.LINE_DASH);
		connection.setArrowTip(3); // start_tip;
		if (connection.getTag().equals(""))
			connection.setTag("uses");
		
		FlagRedraw.setSuper(true);
	}

	void drawDeclare(TypeNode preNode, TypeNode curNode) {	
		ConnectionNode connection = createConnectionPart(preNode, curNode);
		if (connection.getTag().equals(""))
			connection.setTag("declares");
	}
	
	void drawUseVariable(TypeNode preNode, TypeNode curNode) {	
		ConnectionNode connection = createConnectionPart(preNode, curNode);

		connection.setArrowTip(3); // start_tip;
		if (connection.getTag().equals(""))
			connection.setTag("uses as variables");
	}
	
	void drawUseParameter(TypeNode preNode, TypeNode curNode) {	
		ConnectionNode connection = createConnectionPart(preNode, curNode);
		if (connection.getTag().equals(""))
			connection.setTag("uses as parameters");
	}
	
	void drawUseReturn(TypeNode preNode, TypeNode curNode) {		
		ConnectionNode connection = createConnectionPart(preNode, curNode);
		if (connection.getTag().equals(""))
			connection.setTag("uses as return types");
	}
	
	public ConnectionNode createConnectionPart(TypeNode node1, TypeNode node2) {
		return rootNode.addStructuralRelation(node1, node2, "");
	}
	
	public void removeSuperPart(IType type) { // doing
		List<ConnectionNode> dummyconnectionparts = new ArrayList<ConnectionNode>();		

		for (ConnectionNode preConnection: rootNode.getStructuralRelations()) {
			if (preConnection.getDestinationNode().getType().getHandleIdentifier().equals(type.getHandleIdentifier())) {
				if (preConnection.getTag().equals("extends") || preConnection.getTag().equals("implements"))
					dummyconnectionparts.add(preConnection);
			}
		}
		rootNode.removeStructuralRelations(dummyconnectionparts);		
	}

	public void removeConnectionPart(IType type, String tag) { // doing
		List<ConnectionNode> dummyconnectionparts = new ArrayList<ConnectionNode>();		

		for (ConnectionNode preConnection: rootNode.getStructuralRelations()) {
			if (preConnection.getSourceNode().getType().getHandleIdentifier().equals(type.getHandleIdentifier())) {
				if (preConnection.getTag().equals(tag))
					dummyconnectionparts.add(preConnection);
			}
		}
		rootNode.removeStructuralRelations(dummyconnectionparts);
	}
}
