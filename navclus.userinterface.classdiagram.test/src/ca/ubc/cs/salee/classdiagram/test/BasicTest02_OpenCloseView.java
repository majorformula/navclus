package ca.ubc.cs.salee.classdiagram.test;

import junit.framework.TestCase;

import navclus.userinterface.classdiagram.Viewer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

//import ca.ubc.cs.salee.classdiagram.figures.UMLClass_Structure;
import ca.ubc.cs.salee.classdiagram.testutil.SourceModel;
import ca.ubc.cs.salee.classdiagram.testutil.UIModel;


/**
 * The class <code>FavoritesViewTest</code> contains tests 
 * for the class {@link 
 *    com.qualityeclipse.favorites.views.FavoritesView}.
 *
 * @pattern JUnit Test Case
 * @generatedBy CodePro Studio
 */
public class BasicTest02_OpenCloseView extends TestCase
{
	private static final String VIEW_ID = 
		"ca.ubc.cs.salee.classdiagram.view";

	/**
	 * The object that is being tested.
	 *
	 * @see com.qualityeclipse.favorites.views.FavoritesView
	 */
	private Viewer testView;

	/**
   /**
	 * Construct new test instance.
	 *
	 * @param name the test name
	 */
	public BasicTest02_OpenCloseView(String name) {
		super(name);
	}


	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *
	 * @see TestCase#setUp()
	 */
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
		
		PlatformUI
		.getWorkbench()
		.getActiveWorkbenchWindow()
		.getActivePage()
		.bringToTop(testView);

		// Delay for 3 seconds so that 
		// the Favorites view can be seen.
		waitForJobs();
		delay(3000);

		// Add additional setup code here.
	}

	/**
	 * Perform post-test cleanup.
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
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

	/**
	 * Run the view test.
	 */
	public void testView1() {
//		testView.setDrawOption(new DrawOption_Navigation());
		
		UIModel uimodel = new UIModel();
		IFile file1 = SourceModel.getFile("edu.buffalo.cse.green", "src-Green\\edu\\buffalo\\cse\\green", "PlugIn.java");		
		uimodel.openJavaFile(file1);
		delay(3000);
		
		try {
			this.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		delay(3000);
		try {
			this.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		delay(3000);
		
		uimodel.closeJavaFile(file1);
		
		delay(3000);
		
//		testView.countGraphNodes();
//		assertEquals(1,1);
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