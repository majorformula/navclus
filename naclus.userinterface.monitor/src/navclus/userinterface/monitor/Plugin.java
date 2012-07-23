package navclus.userinterface.monitor;

import java.io.FileNotFoundException;

import navclus.userinterface.monitor.patterns.PatternSelector;
import navclus.userinterface.monitor.selections.SelectionKeeper;
import navclus.userinterface.monitor.selections.SelectionMonitor;

import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Plugin extends AbstractUIPlugin {

	public static final String ID_PLUGIN = "navclus.userinterface.monitor";

	private static Plugin plugin;

	/**
	 * Returns the shared instance.
	 */
	public static Plugin getDefault() {
		return plugin;
	}

	private PatternSelector patternSelector;

	private SelectionKeeper selectionKeeper;

	private SelectionMonitor selectionMonitor;

//	private static JavaEditorTextHover1 textHover = null;

	private static IWorkbenchWindow WINDOW = null; // PlatformUI.getWorkbench().getActiveWorkbenchWindow();

	public Plugin() {
		plugin = this;
	}

	public PatternSelector getPatternSelector() {
		return patternSelector;
	}

	public SelectionKeeper getSelectionKeeper() {
		return selectionKeeper;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		try {
			// salee: any initialization...
			Plugin.getDefault().patternSelector = new PatternSelector();
			Plugin.getDefault().patternSelector.initiate();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		MonitorUiPlugin.getDefault().getSelectionMonitors().remove(selectionMonitor);
		plugin = null;

//		UiUsageMonitorPlugin.getDefault().removeMonitoredPreferences(
//				WorkbenchPlugin.getDefault().getPluginPreferences());
//		UiUsageMonitorPlugin.getDefault().removeMonitoredPreferences(
//				ContextCorePlugin.getDefault().getPluginPreferences());
//		UiUsageMonitorPlugin.getDefault().removeMonitoredPreferences(JavaPlugin.getDefault().getPluginPreferences());
//		UiUsageMonitorPlugin.getDefault().removeMonitoredPreferences(
//				WorkbenchPlugin.getDefault().getPluginPreferences());
//		UiUsageMonitorPlugin.getDefault().removeMonitoredPreferences(EditorsPlugin.getDefault().getPluginPreferences());
//		UiUsageMonitorPlugin.getDefault().removeMonitoredPreferences(PDEPlugin.getDefault().getPluginPreferences());
	}

	public static class MylynUseContextStartup implements IStartup {

		public void earlyStartup() {
			final IWorkbench workbench = PlatformUI.getWorkbench();
			workbench.getDisplay().asyncExec(new Runnable() {

				@SuppressWarnings("deprecation")
				public void run() {
					Plugin.getDefault();
					Plugin.getDefault().selectionKeeper = new SelectionKeeper();
					Plugin.getDefault().selectionMonitor = new SelectionMonitor();
					MonitorUiPlugin.getDefault().getSelectionMonitors().add(Plugin.getDefault().selectionMonitor);

					WINDOW = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//					textHover = JavaEditorTextHover1.this;

					// Select Text
////					IEclipsePreferences
//					UiUsageMonitorPlugin.getDefault().addMonitoredPreferences(
//							WorkbenchPlugin.getDefault().getPluginPreferences());
//					// MylarUsageMonitorPlugin.getDefault().addMonitoredPreferences(
//					// MylarUiPlugin.getDefault().getPluginPreferences());
//					UiUsageMonitorPlugin.getDefault().addMonitoredPreferences(
//							JavaPlugin.getDefault().getPluginPreferences());
//					UiUsageMonitorPlugin.getDefault().addMonitoredPreferences(
//							WorkbenchPlugin.getDefault().getPluginPreferences());
//					UiUsageMonitorPlugin.getDefault().addMonitoredPreferences(
//							EditorsPlugin.getDefault().getPluginPreferences());
//					UiUsageMonitorPlugin.getDefault().addMonitoredPreferences(
//							PDEPlugin.getDefault().getPluginPreferences());

				}
			});
		}
	}

}
