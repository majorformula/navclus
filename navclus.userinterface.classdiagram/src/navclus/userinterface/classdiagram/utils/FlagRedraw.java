/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.classdiagram.utils;

public class FlagRedraw {
		
	static boolean bdone = false;
	static boolean bsync = false;
	static boolean bsuper = false;

	public static boolean isSuper() {
		return bsuper;
	}

	public static void setSuper(boolean bsuper) {
		FlagRedraw.bsuper = bsuper;
	}

	public static boolean isDone() {
		return bdone;
	}

	public static void setDone(boolean bdone) {
		FlagRedraw.bdone = bdone;
	}

	public static boolean isSync() {
		return bsync;
	}

	public static void setSync(boolean bsync) {
		FlagRedraw.bsync = bsync;
	}	
}
