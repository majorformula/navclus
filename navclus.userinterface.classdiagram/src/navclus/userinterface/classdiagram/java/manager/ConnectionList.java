/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.classdiagram.java.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IType;

public class ConnectionList {

	private List<ConnectionNode> connections = null;	
	private List<ConnectionNode> connectiontrashes = null;
	
	public ConnectionList() {
		this.connections = new ArrayList<ConnectionNode>();	
		this.connectiontrashes = new ArrayList<ConnectionNode>();
	}

	public List<ConnectionNode> getConnections() {
		return connections;
	}

	public List<ConnectionNode> getTrashConnections() {
		return connectiontrashes;
	}
	
	public ConnectionNode addConnection(TypeNode node1, TypeNode node2, String tag) {
		ConnectionNode connectionpart = new ConnectionNode(node1, node2, tag);		
		this.connections.add(connectionpart);

		return connectionpart;
	}	
	
	public void removeConnection(IType type) { // doing
		List<ConnectionNode> dummyconnectionparts = new ArrayList<ConnectionNode>();		

		for (ConnectionNode preConnection: connections) {
			if (preConnection.getSourceNode().getType().getHandleIdentifier().equals(type.getHandleIdentifier())) {
				dummyconnectionparts.add(preConnection);
			}
			if (preConnection.getDestinationNode().getType().getHandleIdentifier().equals(type.getHandleIdentifier())) {
				dummyconnectionparts.add(preConnection);
			}
		}	

		this.connections.removeAll(dummyconnectionparts);
		this.connectiontrashes.addAll(dummyconnectionparts);		
	}
	
	public void removeConnection(List<ConnectionNode> dummyconnectionparts) { // doing
		this.connections.removeAll(dummyconnectionparts);
		this.connectiontrashes.addAll(dummyconnectionparts);		
	}

	public void clear() {
		this.connections.clear(); 
		this.connectiontrashes.clear();		
	}
	
	public void updateConnection(IType type) { // doing				

		for (ConnectionNode preConnection: connections) {
			if (preConnection.getSourceNode().getType().getHandleIdentifier().equals(type.getHandleIdentifier())) {
				preConnection.setState(STATE.toUpdate);				
			}
			if (preConnection.getDestinationNode().getType().getHandleIdentifier().equals(type.getHandleIdentifier())) {
				preConnection.setState(STATE.toUpdate);	
			}
		}		
	}
	
	public boolean isConnected(TypeNode node1, TypeNode node2)  {		
		// direct-relationships: do not processing anymore if there is a relationship			
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
	
	public void dispose() {
		for (ConnectionNode connectionpart: connections) {
			connectionpart.cleanUp();
		}
		this.connections.clear();
		this.connections = null; 	
		this.connectiontrashes.clear();
		this.connectiontrashes = null;		
	}
}
