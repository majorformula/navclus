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

public enum STATE {
	noChange (0), 
	toCreate (1), 
	toUpdate (2);
	
    STATE(int value) { this.value = value; }

    private final int value;

    public int value() { return value; }
}
