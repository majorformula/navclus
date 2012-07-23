/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package navclus.userinterface.monitor.patterns;

import java.util.LinkedList;

import org.eclipse.jdt.core.IJavaElement;

public class TriggerList {

	private LinkedList<IJavaElement> triggerList;

	public LinkedList<IJavaElement> getTriggerList() {
		return triggerList;
	}

	public void setTriggerList(LinkedList<IJavaElement> triggerList) {
		this.triggerList = triggerList;
	}

	public void add(IJavaElement element) {
		triggerList.add(element);
	}

	public int size() {
		return triggerList.size();
	}

	public void clear() {
		triggerList.clear();
	}
}
