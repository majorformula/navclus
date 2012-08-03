/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.monitor.selections;

import navclus.userinterface.monitor.selections.data.SelectionList;

import org.eclipse.jdt.core.IJavaElement;

public class SelectionKeeper {

	private SelectionList selectionList;

//	private final int K = 2;

	public SelectionKeeper() {
		this.selectionList = new SelectionList();
	}

	public SelectionList getSelectionList() {
		return selectionList;
	}

	public void setSelectionList(SelectionList selectionList) {
		this.selectionList = selectionList;
	}

	public void addSelection(IJavaElement element) {
		try {
			if (element == null) {
				return;
			}

			if (!element.exists()) {
				return;
			}

			if (!checkElementType(element)) {
				return;
			}

			if (selectionList.contain(element)) {
				return;
			}
			selectionList.add(element);
			System.out.println("added: " + element.getElementName());
			printSelectionIfTrigger();
//			printSelectionIfFull();
		} catch (Exception e) {
			System.err.println("Error in SelectionKeeper.addSelection():" + e.getMessage());
			e.printStackTrace();
		}
	}

//	public void addSelection(InteractionEvent interactionEvent) {
//		try {
//
//			// ignore the wrong conditions
//			if (interactionEvent.getKind() != InteractionEvent.Kind.SELECTION) {
//				return;
//			}
//			if (interactionEvent.getStructureHandle() == null) {
//				return;
//			}
//
//			IJavaElement element = JavaCore.create(interactionEvent.getStructureHandle());
//			if (element == null) {
//				return;
//			}
//
//			if (!element.exists()) {
//				return;
//			}
//
//			if (!checkElementType(element)) {
//				return;
//			}
//
//			if (selectionList.contain(element)) {
//				return;
//			}

//			// test if the element is frequent or not
//			if (MylynUseContextPlugin.getDefault().getPatternSelector().IsFrequent(element)) {
//				selectionList.add(element, true);
//				System.out.println("added: " + element.getElementName() + " " + "true");
//			} else {
//				selectionList.add(element, false);
//				System.out.println("added: " + element.getElementName() + " " + "false");
//			}
//			System.out.println("added: " + element.getElementName() + " " + element);
//			printSelectionIfTrigger();
//			printSelectionIfFull();
//		} catch (Exception e) {
//			System.err.println("Error in SelectionKeeper.addSelection():" + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	private boolean checkElementType(IJavaElement element) {

		switch (element.getElementType()) {
		case (IJavaElement.METHOD):
		case (IJavaElement.FIELD):
		case (IJavaElement.TYPE):
		case (IJavaElement.COMPILATION_UNIT):
			return true;
		}

		return false;
	}

//	private void printSelectionIfFull() {
//		if (selectionList.IsFullwithUnmarkedElements()) {
//			System.err.println("The use context is full");
//
//			// clear the screen;
//			Display.getDefault().asyncExec(new Runnable() {
//				public void run() {
////					MylynUseContextPlugin.getDefault().getPatternSelector().clearView();
////	        		System.out.println("hovering: " + curr.getElementName());
//				}
//			});
//
//			selectionList.clear();
//		}
//	}
//
	private void printSelectionIfTrigger() {
		if (selectionList.size() < 3) {
			return;
		}

		// print the use context:
		System.out.println("context");
		selectionList.print();

//		Display.getDefault().asyncExec(new Runnable() {
//			public void run() {
//				Plugin.getDefault()
//						.getPatternSelector()
//						.showSelectedPatterns(selectionList.getMarkedElements());
////        		System.out.println("hovering: " + curr.getElementName());
//			}
//		});

		// clear the screen;

//		selectionList.clear();
	}

}
