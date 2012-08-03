/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.classdiagram.actions;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.java.manager.RootNode;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.Viewer;
//import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;
//import org.eclipse.mylyn.monitor.core.InteractionEvent;


public class RedrawAction extends Action implements IAction {
	
//	protected boolean bLog;
	
	public RedrawAction() {
		super("", AS_RADIO_BUTTON);	
//		this.bLog = true;
	}

	public void run() {
		int drawOption = NavClusView.getDefault().getDrawOption();		
		if (drawOption < 0) return;		
		System.out.println("RedrawAction: action" + NavClusView.getDefault().getDrawOption());

//		if (bLog) {
//			viewer.setDrawOption(this.drawOption);			
//		}
		
		RootNode rootNode = NavClusView.getDefault().getRootNode();		
		if (rootNode == null)
			return;
				
		// remove all nodes
		rootNode.removeAllGraphNodes();
		// draw all nodes
		rootNode.drawGraphNodes();
		
		// remove all connections
		rootNode.removeAllGraphConnections();
		
		switch (drawOption) {
		case 10: // add all connections
			rootNode.drawNavigationalRelations();
			break;
		case 20: // add all connections
			rootNode.drawStructuralRelations();
			break;
		default:
			System.out.println("It's error in run function of RedrawAction");
		}
						
		NavClusView.getDefault().getG().applyLayout();					
//		log();
	}

//	protected void log() {
//		if (bLog == false) { bLog = true; return; }
//		
//		String sAction = "navigated";
//
////		if (viewer.getDrawOption() instanceof DrawOption_Navigation) {
////			sAction = "navigation";
////		}
////		else if (viewer.getUmlclass() instanceof UMLClass_Structure) {
////			sAction = "structure";
////		}
////		else if (viewer.getUmlclass() instanceof UMLClass_All) {
////			sAction = "all";
////		}
////		else {
////			sAction = "error";
////		}
//
//		InteractionEvent interactionEvent 
//		= InteractionEvent.makeCommand(Viewer.ID, "classview ; select_menu ; " 
//				+ sAction + "; " 
//				+ viewer.countGraphNodes());
//		MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);		
//	}
}
