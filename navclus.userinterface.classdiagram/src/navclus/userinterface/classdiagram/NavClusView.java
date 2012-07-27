package navclus.userinterface.classdiagram;

import navclus.userinterface.classdiagram.actions.RedrawAction;
import navclus.userinterface.classdiagram.java.analyzer.RootModel;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.listeners.GraphMouseListener;
import navclus.userinterface.classdiagram.utils.FlagRedraw;
import navclus.userinterface.classdiagram.utils.JavaEditorUtil;
import navclus.userinterface.classdiagram.utils.TypeHistory;
import navclus.userinterface.monitor.listeners.JavaEditorPartListener2;
import navclus.userinterface.monitor.listeners.JavaEditorSelectionListener;
import navclus.userinterface.monitor.patterns.PatternSelector;
import navclus.userinterface.monitor.selections.SelectionKeeper;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
//import navclus.userinterface.monitor.patterns.PatternSelector;
//import org.eclipse.mylyn.monitor.core.InteractionEvent;
//import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;

//import ca.ubc.cs.salee.classdiagram.actions.RedrawActionwoLayout;
//import ca.ubc.cs.salee.classdiagram.listeners.JavaChangeListener;
//import ca.ubc.cs.salee.classdiagram.listeners.JobEventListener;

public class NavClusView extends ViewPart {

	public static final String ID = "navclus.userinterface.classdiagram.navclusview"; //$NON-NLS-1$	
    public final static int DRAW_NAVIGATIONAL_RELATIONSHIP  = 10;
    public final static int DRAW_STRUCTURAL_RELATIONSHIP  = 20;
    
    /**
	 * Returns the shared instance.
	 */
	public static NavClusView getDefault() {
		if (viewer == null)
			viewer = new NavClusView();
		
		return viewer;
	}

    private PatternSelector patternSelector;

    private SelectionKeeper selectionKeeper;
//
//    private SelectionMonitor selectionMonitor;

    private static NavClusView viewer;
	
	JavaEditorPartListener2 javaeditorpartlistner2;

	JavaEditorSelectionListener javaeditorselectionlistener;
	
	//	JobEventListener jobeventlistener;
//	JavaChangeListener javachangelistener;	
	private IAction action00;
//	private IAction action10;
	private IAction action20;
	
	//	private IAction action30;
	private IWorkbenchWindow WINDOW = null;	
private RootModel rootmodel = null;

	private Graph g = null;
	public NavClusView() {
		viewer = this;
	} 
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
			
	public int countGraphConnections() {	
		return g.getConnections().size();
	}
	
	public int countGraphNodes() {	
		return g.getNodes().size();
	}
	
	@Override
	public void createPartControl(final Composite parent) {		
		WINDOW = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		g = new Graph(parent, SWT.NONE);
		
//		g.setLayoutAlgorithm(new		
//				HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);		
		
//		g.setLayoutAlgorithm(new 
//				CompositeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING, 
//				new LayoutAlgorithm[] { 
//						new HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), 
//						new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING) }), true); 
		
		g.setLayoutAlgorithm(new 
				HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);	
		g.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED); 		
		
		// Error: we should stop the listener before closing this program
		g.getLightweightSystem().getRootFigure().addMouseListener(new GraphMouseListener());

		rootmodel = new RootModel(new RootNode());
				
		makeActions();				// for menu
		contributeToActionBars();	// for menu
		hookPartAction();			// for window
//		SynchronizeWithEditor();	// for view
		
//		InteractionEvent interactionEvent 
//		= InteractionEvent.makePreference(ID, "classview ; open_view ; " + countGraphNodes());
//		MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);
	}

	@Override
	public void dispose() {
		super.dispose();

		rootmodel.cleanUp();
		rootmodel = null;

		// Open & Close Files
		WINDOW.getPartService().removePartListener(javaeditorpartlistner2);

		// Select Text
		WINDOW.getSelectionService().removePostSelectionListener(javaeditorselectionlistener);
		
//		// Jobs
//		Job.getJobManager().removeJobChangeListener(jobeventlistener);
//		
//		// change java elements
//		JavaCore.removeElementChangedListener(javachangelistener);
		
//		InteractionEvent interactionEvent 
//		= InteractionEvent.makePreference(ID, "classview ; close_view ; " + countGraphNodes());
//		MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);
		
		this.patternSelector = null;
		this.selectionKeeper = null;
		
		g.dispose();
	}

	private void fillLocalPullDown(IMenuManager manager) {
//		manager.add(action10);
		manager.add(action20);
		manager.add(new Separator());
		manager.add(action00);
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(action10);
		manager.add(action20);	
		manager.add(action00);
	}
	
	public int getDrawOption() {
		
//		if (action10.isChecked())
//			return 10;
//		else 
		if (action20.isChecked())
			return 20;
		else {
//			System.out.println("Action is:" )
			return -1;
		}

	}
	
	public Graph getG() {
		return g;
	}
	
	public PatternSelector getPatternSelector() {
    	return patternSelector;
    }
	
	public RootModel getRootModel() {
		return rootmodel;
	}

	public RootNode getRootNode() {
		if (rootmodel == null)
			return null;

		return rootmodel.getRootNode();
	}
	
	public SelectionKeeper getSelectionKeeper() {
    	return selectionKeeper;
    }

	public IWorkbenchWindow getWINDOW() {
		if (WINDOW != null)
			return WINDOW;
		else
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	private void hookPartAction() {
		
		this.patternSelector = new PatternSelector();
		this.selectionKeeper = new SelectionKeeper();
		
		// Open & Close Files
		javaeditorpartlistner2 = new JavaEditorPartListener2();
		WINDOW.getPartService().addPartListener(javaeditorpartlistner2);

//		// change java elements
//		javachangelistener = new JavaChangeListener(this);
//		JavaCore.addElementChangedListener(javachangelistener, ElementChangedEvent.POST_RECONCILE);
//		
		// Select Text
		javaeditorselectionlistener = new JavaEditorSelectionListener();
		WINDOW.getSelectionService().addPostSelectionListener(javaeditorselectionlistener);		
//		
//		// Jobs
//		jobeventlistener = new JobEventListener();
//		Job.getJobManager().addJobChangeListener(jobeventlistener);
	}

	private void makeActions() {
		action00 = new Action() {
			public void run() {				
				g.applyLayout();
			}
		};
		action00.setText("Apply Layout");
		action00.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				 getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

//		action10 = new RedrawAction(this);  
//		action10.setText("Show Navigation History");
//		action10.setImageDescriptor(ImageDescriptor.createFromImage(
//				new Image(Display.getDefault(), 
//						this.getClass().getResourceAsStream("admin1_face.gif"))));

		action20 = new RedrawAction();   
		action20.setText("Show Structural Information");
		action20.setImageDescriptor(ImageDescriptor.createFromImage(
				new Image(Display.getDefault(), 
						this.getClass().getResourceAsStream("admin2_face.gif"))));
		
		
		action20.setChecked(true);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
	}

	public void setG(Graph g) {
		this.g = g;
	}
	
	public void setRootModel(RootModel rootmodel) {
		this.rootmodel = rootmodel;
	}
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
				g.getShell(), 
				"Class View",
				message);
	}
	
	private void SynchronizeWithEditor() {
		FlagRedraw.setSync(false);
		
		IWorkbenchPage page = WINDOW.getActivePage();
		if (page == null) return;
	
		IEditorReference [] editorreferences = page.getEditorReferences();
		for (IEditorReference editorreference: editorreferences) {
			IJavaElement element = 
				JavaEditorUtil.getJavaElement(editorreference);

			if (element == null) 
				continue;
			else {			
				try {
					rootmodel.addJavaFile(element);
				} catch (JavaModelException e) {
					e.printStackTrace();
				}			
				FlagRedraw.setSync(true);
			}
		}		
		IJavaElement finalJavaElement = JavaEditorUtil.getTopElement();
		TypeHistory.setCurElement(finalJavaElement);
	}
	
}