package ca.ubc.cs.salee.classdiagram.java.manager;


public class ConnectionNode {
	private TypeNode sourceNode;
	private TypeNode destinationNode;	
	private String tag;
	private int arrowTip = 2; // 0: none, 1: start, 2: end, 3: both
	
	boolean isDirty = true;	
	STATE state = STATE.noChange;
	
	public ConnectionNode(TypeNode sourceNode, 
			TypeNode destinationNode,
			String tag) {
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.tag = tag;
		
		state = STATE.toCreate;
	}

	public TypeNode getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(TypeNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	public TypeNode getDestinationNode() {
		return destinationNode;
	}

	public void setDestinationNode(TypeNode destinationPart) {
		this.destinationNode = destinationPart;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getArrowTip() {
		return arrowTip;
	}

	public void setArrowTip(int arrowTip) {
		this.arrowTip = arrowTip;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}		

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}
	
	public void cleanUp() {
		sourceNode = null;
		destinationNode = null;
		tag = null;
	}
}
