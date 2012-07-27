package navclus.userinterface.classdiagram.java.manager;

import java.util.ArrayList;
import java.util.List;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.classfigure.UMLNode;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.IType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;



public class RootNode {

	private TypeNodeList typeNodeList;
	private ConnectionList structuralRelationList;
	private ConnectionList navigationalRelationList;	
	private NodeMapper nodeMapper;
	
	boolean isDirty = false;

	public RootNode() {
		this.typeNodeList = new TypeNodeList();
		this.structuralRelationList = new ConnectionList();
		this.navigationalRelationList = new ConnectionList();	
		this.nodeMapper = new NodeMapper();
	}

	public ArrayList<TypeNode> getTypeNodes() {
		return typeNodeList.getTypeNodes();
	}
	
	public boolean contain(IType curType) {
		if (typeNodeList.contain(curType))
			return true;
		else
			return false;		
	}
	
	public TypeNode findNode(IType curType) {	
		return typeNodeList.findNode(curType);
	}
	
	public TypeNode addNode(IType curType) {
		return typeNodeList.addNode(curType);
	}
	
	public boolean removeNodewithChildren(IType curType) {
		TypeNode curNode = typeNodeList.findNode(curType);		
		if (curNode == null) return false;
		
		// delete children
		for (IType childtype: curNode.embeddedTypes) {
			removeNode(childtype);
		}
		
		return removeNode(curType);
	}

	public boolean removeNode(IType curType) {
		TypeNode curNode = typeNodeList.findNode(curType);
		if (curNode == null) return false;
		
		removeStructuralRelations(curType);	
		return typeNodeList.removeNode(curNode);
	}
	


	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

//	public void updateAddView() {	
//		
//		if (Display.getCurrent() != null) {
//			synchronizeNodesinView();
//			(new RedrawAction(viewer)).run();
//		}
//		else {
//			// draw the new nodes & connections 
//			Display.getDefault().asyncExec(new Runnable() {
//				public void run() {					
//					synchronizeNodesinView();
//					(new RedrawAction(viewer)).run();
//				}
//			});
//		}
//	}
	
//	public void updateDeleteView() {		
//		if (Display.getCurrent() != null) {
//			synchronizeNodesinView();
//			(new RedrawActionwoLayout(viewer)).run();
//		}
//		else {
//			// draw the new nodes & connections 
//			Display.getDefault().asyncExec(new Runnable() {
//				public void run() {					
//					synchronizeNodesinView();
//					(new RedrawActionwoLayout(viewer)).run();
//				}
//			});
//		}
//	}
	
//	public void synchronizeNodesinView() {
//		deleteNodesinView();
//		createNodesinView();
//	}
	
		
	public void updateNode(final TypeNode typenode) {				
		if (Display.getCurrent() != null) {
			// delete the node
			Point curPoint = nodeMapper.removeAtPosition(typenode);
		
			// create the node
			UMLNode curNode = nodeMapper.create(typenode, curPoint);
			curNode.setText((typenode.getType().getHandleIdentifier()));							
			
//			updateConnection(node.getType());
//			createConnection();
		}
		else {
			// draw the new nodes & connections 
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {					
					// delete the node
					Point curPoint = nodeMapper.removeAtPosition(typenode);
									
					// create the node
					UMLNode curNode = nodeMapper.create(typenode, curPoint);
					curNode.setText((typenode.getType().getHandleIdentifier()));
					
//					updateConnection(node.getType());
//					createConnection();
				}
			});
		}
	}
	
	public void createNode(final UMLNode umlnode) {	
		
		if (Display.getCurrent() != null) {
			umlnode.dispose();
		}
		else {
			// draw the new nodes & connections 
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {					
					umlnode.dispose();
				}
			});
		}
	}
		
	public UMLNode getUMLNode(TypeNode node) {
		return nodeMapper.get(node);
	}
	
	public TypeNode getTypeNode(UMLNode node) {
		System.err.println("test:getTypeNode");
		return nodeMapper.get(node);
	}
	

		
	public void removeAllGraphConnections() {
		List<GraphConnection> connections = NavClusView.getDefault().getG().getConnections();		
		
		while (connections.size() > 0) {
			GraphConnection connection = (GraphConnection) connections.get(0);
			if (connection != null && !connection.isDisposed()) {
				connection.dispose();
			}
		}
	}
	
	public void removeAllGraphNodes() {	
		List<GraphNode> nodes = NavClusView.getDefault().getG().getNodes();		
		
		while (nodes.size() > 0) {
			GraphNode node = (GraphNode) nodes.get(0);
			if (node != null && !node.isDisposed()) {
				node.dispose();
			}
		}

	}
		
	public void drawGraphNodes() {
		if (typeNodeList.getSize() == 0) return;
				
		for (TypeNode node: typeNodeList.getTypeNodes()) { // it is modifiable
			nodeMapper.create(node);
			
			
//			UMLNode preGraphNode = nodeMapper.get(typeNode);
//			if (preGraphNode == null) continue;
//			Point prePoint = preGraphNode.getLocation();
//			
//			IType preType = typeNode.getType();
//			if (preType == null) continue;
//			
			// setCustomFigure
//			IFigure classFigure = (new ClassFigureCreator()).createClassFigure(typeNode);
//			if (classFigure == null) continue;
//			
//			preGraphNode.setUMLNode(viewer.getG(), classFigure);			
//			preGraphNode.setText((preType.getHandleIdentifier()));
//			preGraphNode.setLocation(prePoint.x, prePoint.y);
		}	
	}
	
//	public void createNodesinView() {
//		if (typeNodeList.getSize() == 0) return;
//		
//		for (TypeNode typenode: typeNodeList.getTypeNodes()) {
//			nodeMapper.create(typenode);
//			
////			switch (typenode.getState()) {
////			case toCreate: // create the new node		
////				nodeMapper.create(typenode);
////				typenode.setState(STATE.noChange);
////				break;
////			case toUpdate: // keeping the original position				
////				updateNode(typenode);			
////				typenode.setState(STATE.noChange);				
////				break;
////			}
//		}
//	}
	
	public void printNodes() {
		System.out.println("Type Nodes are:");
		
		for (TypeNode node: typeNodeList.getTypeNodes()) {
			IType type = node.getType();
			System.out.println(type.getElementName());
		}
	}
	
	/**************************************************************************
	 * 		              Structural Relationships                            *
	 * ************************************************************************/
	public void drawStructuralRelations() {
		List<ConnectionNode> connectionNodes = (List<ConnectionNode>) this.getStructuralRelations(); // salee - error
		for (ConnectionNode connectionNode: connectionNodes) {
			UMLNode sourceNode = this.getUMLNode(connectionNode.getSourceNode());
			UMLNode destinationNode = this.getUMLNode(connectionNode.getDestinationNode());
			if ((sourceNode == null) || (destinationNode == null) )continue;			
			
			// draw a connection
			GraphConnection curConnection = new GraphConnection(NavClusView.getDefault().getG(), SWT.NONE, 
												sourceNode, destinationNode);			
			curConnection.setText(connectionNode.getTag());
			curConnection.setArrowTip(connectionNode.getArrowTip());
		}
	}
	
	public List<ConnectionNode> getStructuralRelations() {
		return structuralRelationList.getConnections();
	}

	public ConnectionNode addStructuralRelation(TypeNode node1, TypeNode node2, String tag) {
		return structuralRelationList.addConnection(node1, node2, tag);
	}	
	
	public void removeStructuralRelations(IType type) { // doing
		structuralRelationList.removeConnection(type);
	}
	
	public void removeStructuralRelations(List<ConnectionNode> dummyconnectionparts) { // doing
		structuralRelationList.removeConnection(dummyconnectionparts);
	}
	
	public void updateStructuralRelations(IType type) { // doing		
		structuralRelationList.updateConnection(type);
	}
	
	public boolean isStructurallyRelated(TypeNode node1, TypeNode node2)  {		
		return structuralRelationList.isConnected(node1, node2);
	}
	
	/**************************************************************************
	 * 		              Navigational Relationships                          *
	 * ************************************************************************/
	public void drawNavigationalRelations() {
		List<ConnectionNode> connectionNodes = (List<ConnectionNode>) this.getNavigationalRelations(); // salee - error
		for (ConnectionNode connectionNode: connectionNodes) {
			UMLNode sourceNode = this.getUMLNode(connectionNode.getSourceNode());
			UMLNode destinationNode = this.getUMLNode(connectionNode.getDestinationNode());
			if ((sourceNode == null) || (destinationNode == null) )continue;			
			
			// draw a connection
			GraphConnection curConnection = new GraphConnection(NavClusView.getDefault().getG(), SWT.NONE, 
												sourceNode, destinationNode);			
			curConnection.setText(connectionNode.getTag());
			curConnection.setArrowTip(connectionNode.getArrowTip());
		}
	}
	
	public List<ConnectionNode> getNavigationalRelations() {
		return navigationalRelationList.getConnections();
	}

	public ConnectionNode addNavigationalRelation(TypeNode node1, TypeNode node2, String tag) {
		return navigationalRelationList.addConnection(node1, node2, tag);
	}	
	
	public void removeNavigationalRelations(IType type) { // doing
		navigationalRelationList.removeConnection(type);
	}
	
	public void removeNavigationalRelations(List<ConnectionNode> dummyconnectionparts) { // doing
		navigationalRelationList.removeConnection(dummyconnectionparts);
	}
	
	public void updateNavigationalRelations(IType type) { // doing		
		navigationalRelationList.updateConnection(type);
	}
	
	public boolean isNavigated(TypeNode node1, TypeNode node2)  {		
		return navigationalRelationList.isConnected(node1, node2);
	}
	
	/**************************************************************************
	 * 		            		  Both Relationships                          *
	 * ************************************************************************/
	
//	public void deleteNodesinView() {
//		if (viewer == null) return;	
//		
//		for (TypeNode typeNode: typeNodeList.getTrashNodes()) {
//			nodeMapper.remove(typeNode);
//		}
//		typeNodeList.getTrashNodes().clear();
//		structuralRelationList.getTrashConnections().clear(); // potential error, because it does not manage connectiontrashlist
//		navigationalRelationList.getTrashConnections().clear(); 
//	}
	
	public void clear() {
		if (NavClusView.getDefault() == null) return;	
		typeNodeList.clear();
		structuralRelationList.clear();
		navigationalRelationList.clear();
		nodeMapper.removeAll();
		
		this.removeAllGraphConnections();
		this.removeAllGraphNodes();
	}
	
	public void dispose() {
		typeNodeList.dispose();
		structuralRelationList.dispose();
		navigationalRelationList.dispose();
		nodeMapper.clear();		
	}
}
