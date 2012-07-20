/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

package navclus.userinterface.classdiagram.actions;

import navclus.userinterface.classdiagram.Viewer;
import navclus.userinterface.classdiagram.java.manager.RootNode;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;
import org.eclipse.mylyn.monitor.core.InteractionEvent;


public class RedrawAction extends Action implements IAction {

	protected Viewer viewer;
//	protected boolean bLog;
	
	public RedrawAction(Viewer viewer) {
		super("", AS_RADIO_BUTTON);	
		this.viewer = viewer;	
//		this.bLog = true;
	}

	public void run() {
		int drawOption = viewer.getDrawOption();		
		if (drawOption < 0) return;		
		System.out.println("RedrawAction: action" + viewer.getDrawOption());

//		if (bLog) {
//			viewer.setDrawOption(this.drawOption);			
//		}
		
		RootNode rootNode = viewer.getRootNode();		
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
						
		viewer.getG().applyLayout();					
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
