/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

package navclus.userinterface.classdiagram;

import navclus.userinterface.classdiagram.actions.RedrawAction;
import navclus.userinterface.classdiagram.classfigure.ClassFigureCreator;
import navclus.userinterface.classdiagram.java.analyzer.RootModel;
import navclus.userinterface.classdiagram.java.analyzer.TypeModel;
import navclus.userinterface.classdiagram.java.manager.RootNode;
import navclus.userinterface.classdiagram.listeners.GraphMouseListener;
import navclus.userinterface.classdiagram.listeners.JavaEditorPartListener2;
import navclus.userinterface.classdiagram.listeners.JavaEditorSelectionListener;
import navclus.userinterface.classdiagram.utils.FlagRedraw;
import navclus.userinterface.classdiagram.utils.JavaEditorUtil;
import navclus.userinterface.classdiagram.utils.TypeHistory;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
//import org.eclipse.mylyn.monitor.core.InteractionEvent;
//import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;
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

//import ca.ubc.cs.salee.classdiagram.actions.RedrawActionwoLayout;
//import ca.ubc.cs.salee.classdiagram.listeners.JavaChangeListener;
//import ca.ubc.cs.salee.classdiagram.listeners.JobEventListener;

public class NavClusView extends ViewPart {

	public static final String ID = "ca.ubc.cs.salee.classdiagram.view"; //$NON-NLS-1$	
    public final static int DRAW_NAVIGATIONAL_RELATIONSHIP  = 10;
    public final static int DRAW_STRUCTURAL_RELATIONSHIP  = 20;
	
	private static NavClusView viewer;

	public NavClusView() {
		viewer = this;
	}
	
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
		g.getLightweightSystem().getRootFigure().addMouseListener(new GraphMouseListener(this));

		rootmodel = new RootModel(new RootNode(this));
				
		makeActions();				// for menu
		contributeToActionBars();	// for menu
		hookPartAction();			// for window
//		SynchronizeWithEditor();	// for view
		
//		InteractionEvent interactionEvent 
//		= InteractionEvent.makePreference(ID, "classview ; open_view ; " + countGraphNodes());
//		MonitorUiPlugin.getDefault().notifyInteractionObserved(interactionEvent);
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

		action20 = new RedrawAction(this);   
		action20.setText("Show Structural Information");
		action20.setImageDescriptor(ImageDescriptor.createFromImage(
				new Image(Display.getDefault(), 
						this.getClass().getResourceAsStream("admin2_face.gif"))));
		
		
		action20.setChecked(true);
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
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
	
	private void SynchronizeWithEditor() {
		FlagRedraw.setSync(false);
		
		IWorkbenchPage page = WINDOW.getActivePage();
		if (page == null) return;

		JavaEditorUtil javaeditorutil = new JavaEditorUtil();
		
		IEditorReference [] editorreferences = page.getEditorReferences();
		for (IEditorReference editorreference: editorreferences) {
			IJavaElement element = 
				javaeditorutil.getJavaElement(editorreference);

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
		IJavaElement finalJavaElement = javaeditorutil.getTopElement();
		TypeHistory.setCurElement(finalJavaElement);
	}
	
	private void hookPartAction() {	
		
		// Open & Close Files
		javaeditorpartlistner2 = new JavaEditorPartListener2(this);
		WINDOW.getPartService().addPartListener(javaeditorpartlistner2);

//		// change java elements
//		javachangelistener = new JavaChangeListener(this);
//		JavaCore.addElementChangedListener(javachangelistener, ElementChangedEvent.POST_RECONCILE);
//		
		// Select Text
		javaeditorselectionlistener = new JavaEditorSelectionListener(this);
		WINDOW.getSelectionService().addPostSelectionListener(javaeditorselectionlistener);		
//		
//		// Jobs
//		jobeventlistener = new JobEventListener();
//		Job.getJobManager().addJobChangeListener(jobeventlistener);
	}
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
				g.getShell(), 
				"Class View",
				message);
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
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
		
		g.dispose();
	}

	public RootModel getRootModel() {
		return rootmodel;
	}
	
	public RootNode getRootNode() {
		if (rootmodel == null)
			return null;

		return rootmodel.getRootNode();
	}

	public void setRootModel(RootModel rootmodel) {
		this.rootmodel = rootmodel;
	}

	public IWorkbenchWindow getWINDOW() {
		if (WINDOW != null)
			return WINDOW;
		else
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	public Graph getG() {
		return g;
	}

	public void setG(Graph g) {
		this.g = g;
	}

	public int countGraphNodes() {	
		return g.getNodes().size();
	}
	
	public int countGraphConnections() {	
		return g.getConnections().size();
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
	
	/**
	 * Returns the shared instance.
	 */
	public static NavClusView getDefault() {	
		return viewer;
	}
	
}