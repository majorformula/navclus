package ca.ubc.cs.salee.classdiagram.view.test;

import junit.framework.TestCase;

import navclus.userinterface.classdiagram.Viewer;
import navclus.userinterface.classdiagram.utils.TypeHistory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import ca.ubc.cs.salee.classdiagram.testutil.SourceModel;
import ca.ubc.cs.salee.classdiagram.testutil.UIModel;

public class PreviousTypeTest extends TestCase {
	
	private static final String VIEW_ID = 
		"ca.ubc.cs.salee.classdiagram.view";

	UIModel uimodel = new UIModel();	
	
	/**
	 * The object that is being tested.
	 *
	 * @see com.qualityeclipse.favorites.views.FavoritesView
	 */
	private Viewer testView;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// Initialize the test fixture for each test 
		// that is run.
		waitForJobs();
		testView = (Viewer) 
		PlatformUI
		.getWorkbench()
		.getActiveWorkbenchWindow()
		.getActivePage()
		.showView(VIEW_ID, null, IWorkbenchPage.VIEW_CREATE);

		// Delay for 3 seconds so that 
		// the Favorites view can be seen.
		waitForJobs();
		delay(3000);

		// Add additional setup code here.
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// Dispose of test fixture.
		waitForJobs();
		PlatformUI
		.getWorkbench()
		.getActiveWorkbenchWindow()
		.getActivePage()
		.hideView(testView);
		// Add additional teardown code here.
	}

	public void testSetPreElement1() {
		TypeHistory previoustype = TypeHistory.getPreviousType();
		
		// test initial mode
		assertNotNull(previoustype);
		assertNull(previoustype.getPreElement());
		assertNull(previoustype.getPreType());
		
		// open a file
		IFile file = SourceModel.getFile("edu.buffalo.cse.green", "src-Green\\edu\\buffalo\\cse\\green", "PlugIn.java");

		uimodel.openJavaFile(file);
		waitForJobs();
		delay(3000);
		
		assertNotNull(previoustype.getPreType());
		assertEquals("PlugIn", previoustype.getPreType().getElementName());
		
		uimodel.closeJavaFile(file);
		waitForJobs();
		delay(3000);
		
		// test 2nd mode
		assertNotNull(previoustype);
		assertNotNull(previoustype.getPreElement());
		assertNotNull(previoustype.getPreType());
		
		// open a file #1
		IFile file1 = SourceModel.getFile("edu.buffalo.cse.green", "src-Green\\edu\\buffalo\\cse\\green", "PlugIn.java");
		uimodel.openJavaFile(file1);
		waitForJobs();
		delay(3000);
		
		// open a file #2
		IFile file2 = SourceModel.getFile("edu.buffalo.cse.green", "src-Green\\edu\\buffalo\\cse\\green", "JavaModelListener.java");	
		uimodel.openJavaFile(file2);
		waitForJobs();
		delay(3000);
		
		assertNotNull(previoustype.getPreType());
		assertEquals("JavaModelListener", previoustype.getPreType().getElementName());
		
		uimodel.closeJavaFile(file1);
		uimodel.closeJavaFile(file2);
		waitForJobs();
		delay(3000);
		
	}
		
	/**
	 * Process UI input but do not return for the 
	 * specified time interval.
	 * 
	 * @param waitTimeMillis the number of milliseconds 
	 */
	private void delay(long waitTimeMillis) {
		Display display = Display.getCurrent();

		// If this is the UI thread, 
		// then process input.
		if (display != null) {
			long endTimeMillis = 
				System.currentTimeMillis() + waitTimeMillis;
			while (System.currentTimeMillis() < endTimeMillis)
			{
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.update();
		}

		// Otherwise, perform a simple sleep.
		else {
			try {
				Thread.sleep(waitTimeMillis);
			}
			catch (InterruptedException e) {
				// Ignored.
			}
		}
	}

	/**
	 * Wait until all background tasks are complete.
	 */
	public void waitForJobs() {
		while (Job.getJobManager().currentJob() != null)
			delay(1000);
	}
}
