package old.test.reader;

import renewed.in.reader.StructureHandlePrinter;

public class StructureHandlerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String element1 = "=org.eclipse.mylyn.jira.core/src&amp;lt;org.eclipse.mylyn.internal.jira.core.util{JiraUtil.java#"; 
		String element2 = "org.eclipse.mylyn.tasks.ui/src&amp;lt;org.eclipse.mylyn.internal.tasks.ui.wizards{NewRepositoryWizard.java[NewRepositoryWizard~performFinish";
		String element3 = "=org.eclipse.mylyn.tasks.ui/src&amp;lt;org.eclipse.mylyn.tasks.ui.wizards{AbstractRepositorySettingsPage.java";
		String element4 = "=org.eclipse.mylyn.bugzilla.ui/usr/local/eclipse-3.4M6-I20080428-0800/plugins/org.eclipse.jface_3.4.0.I20080427-2000.jar&amp;lt;org.eclipse.jface.wizard(WizardPage.class[WizardPage~isPageComplete";
		String element5 = "/org.eclipse.mylyn.tasks.ui/plugin.xml;-987802509";		
		String element6 = "org.eclipse.mylyn.tasks.ui/src&lt;org.eclipse.mylyn.tasks.ui{TasksUiPlugin.java[TasksUiPlugin^MAX_CHANGED_ATTRIBUTES";
		
		String element7 = "=org.eclipse.mylyn.sandbox.dev/src&lt;org.eclipse.mylyn.internal.sandbox.dev.actions{TestUiAction.java[TestUiAction";
		String element8 = "=org.eclipse.mylyn.tasks.ui/src&lt;org.eclipse.mylyn.tasks.ui{TasksUiPlugin.java[TasksUiPlugin~getChangedDescription~QRepositoryTaskData;~QRepositoryTaskData;";
		String element9 = "=org.eclipse.mylyn.tasks.ui/src&lt;org.eclipse.mylyn.tasks.ui{TasksUiPlugin.java[TasksUiPlugin";
		String element10 = "=org.eclipse.mylyn.tasks.ui/src&lt;org.eclipse.mylyn.tasks.ui{TasksUiPlugin.java[TasksUiPlugin[TasksUiInitializationJob~runInUIThread~QIProgressMonitor;";
		String element11 = "=org.eclipse.mylyn.tasks.ui/src&lt;org.eclipse.mylyn.tasks.ui{TasksUiPlugin.java[TasksUiPlugin~getChangedAttributes~QRepositoryTaskData;~QRepositoryTaskData;";
		
		String element12  = "=org.eclipse.mylyn.tasks.ui/src&amp;lt;org.eclipse.mylyn.internal.tasks.ui.util{TasksUiExtensionReader.java[TasksUiExtensionReader~initStartupExtensions~QTaskListExternalizer;~QTaskListElementImporter;" ;
		String element13  = "=org.eclipse.mylyn.bugzilla.tests/src&amp;lt;org.eclipse.mylyn.bugzilla.tests{BugzillaTaskListTest.java"; 
		String element14  = "=org.eclipse.mylyn.tasks.tests/src&amp;lt;org.eclipse.mylyn.tasks.tests{TaskListTest.java[TaskListTest";
		String element15  = "http://www.solarmetric.com/solarmetric_jump_page/";
		
		
		StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();
		String element = element12;
		
		System.out.println(element);
		System.out.println(structureHandlePrinter.toElement(element));

//		String sProgramElement = element4;
		
//		sProgramElement = sProgramElement.replace("src", " ");
//		sProgramElement = sProgramElement.replace("&amp", " ");
//		sProgramElement = sProgramElement.replace("lt", " ");
//		sProgramElement = sProgramElement.replace("/", " ");
//		sProgramElement = sProgramElement.replace(";", " ");
//		sProgramElement = sProgramElement.replace("{", " ");
//		sProgramElement = sProgramElement.replace("(", " ");
//		sProgramElement = sProgramElement.replace("[", " ");
//		sProgramElement = sProgramElement.replace("~", " ");
//		sProgramElement = sProgramElement.replace("^", " ");
//		sProgramElement = sProgramElement.replace("#", " ");
//		sProgramElement = sProgramElement.replace("=", " ");
//		
//		System.out.println(sProgramElement);
	}

}
