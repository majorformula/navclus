package ca.ubc.cs.salee.classdiagram.view.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllViewTest {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ClassView");

		// $JUnit-BEGIN$
		suite.addTestSuite(PreviousTypeTest.class);
		suite.addTestSuite(JavaEditorPartListenerTest.class);
		// $JUnit-END$

		return suite;
	}
	
}
