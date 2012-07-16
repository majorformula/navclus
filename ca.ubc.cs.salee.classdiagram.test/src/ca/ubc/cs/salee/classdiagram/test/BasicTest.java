package ca.ubc.cs.salee.classdiagram.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BasicTest {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ClassView");

		// $JUnit-BEGIN$
		suite.addTestSuite(BasicTest01_OpenCloseOpen.class);
		suite.addTestSuite(BasicTest02_OpenCloseView.class);
		// $JUnit-END$

		return suite;
	}
	
}
