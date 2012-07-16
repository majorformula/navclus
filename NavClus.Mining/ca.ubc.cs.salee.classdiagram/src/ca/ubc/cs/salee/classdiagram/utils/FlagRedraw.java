package ca.ubc.cs.salee.classdiagram.utils;

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
