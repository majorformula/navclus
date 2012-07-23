package navclus.userinterface.classdiagram.test;

import junit.framework.TestCase;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.testutil.SourceModel;
import navclus.userinterface.classdiagram.testutil.UIModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.DialogUtil;
import org.eclipse.ui.part.FileEditorInput;


/**
 * The class <code>FavoritesViewTest</code> contains tests 
 * for the class {@link 
 *    com.qualityeclipse.favorites.views.FavoritesView}.
 *
 * @pattern JUnit Test Case
 * @generatedBy CodePro Studio
 */
public class BasicTest01_OpenCloseOpen extends TestCase
{
	private static final String VIEW_ID = 
		"navclus.userinterface.classdiagram.navclusview";

	/**
	 * The object that is being tested.
	 *
	 * @see com.qualityeclipse.favorites.views.FavoritesView
	 */
	private NavClusView testView;

	/**
   /**
	 * Construct new test instance.
	 *
	 * @param name the test name
	 */
	public BasicTest01_OpenCloseOpen(String name) {
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
		testView = (NavClusView) 
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
		UIModel uimodel = new UIModel();
		IFile file = SourceModel.getFile("JHotDraw", "src\\org\\jhotdraw\\samples\\net", "NetApp.java");
	
		uimodel.openJavaFile(file);
		delay(2000);
		
//		uimodel.closeJavaFile(file);		
//		delay(2000);
//		
//		uimodel.openJavaFile(file);
//		delay(2000);		
		
		assertEquals(1, testView.countGraphNodes());
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