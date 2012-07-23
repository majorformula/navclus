package navclus.userinterface.monitor.selections;

import navclus.userinterface.classdiagram.utils.JavaEditorUtil;
import navclus.userinterface.monitor.Plugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICodeAssist;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.actions.SelectionConverter;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.context.core.IInteractionElement;
import org.eclipse.mylyn.internal.monitor.ui.MonitorUiPlugin;
import org.eclipse.mylyn.monitor.core.InteractionEvent;
import org.eclipse.mylyn.monitor.ui.AbstractUserInteractionMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

/**
 * Limited to Java selections.
 * 
 * @author Seonah Lee
 */
public class SelectionMonitor extends AbstractUserInteractionMonitor {

	public SelectionMonitor() {
		super();
		this.javaeditorutil = new JavaEditorUtil();
	}

	JavaEditorUtil javaeditorutil;

	private IJavaElement lastSelectedElement = null;

	private static final String STRUCTURE_KIND_JAVA = "java";

	private static final String ID_JAVA_UNKNOWN = "(non-source element)";

	private static final Object ID_JAVA_UNKNOW_OLD = "(non-existing element)";

	@Override
	protected void handleWorkbenchPartSelection(IWorkbenchPart part, ISelection selection, boolean contributeToContext) {
		// ignored, since not using context monitoring facilities
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		String structureKind = InteractionEvent.ID_UNKNOWN;
		String elementHandle = InteractionEvent.ID_UNKNOWN;

		// salee: interactionKind = selection
		InteractionEvent.Kind interactionKind = InteractionEvent.Kind.SELECTION;

		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			Object selectedObject = structuredSelection.getFirstElement();
			if (selectedObject == null) {
				return;
			}
			if (selectedObject instanceof IJavaElement) {
				IJavaElement javaElement = (IJavaElement) selectedObject;
				structureKind = STRUCTURE_KIND_JAVA;
				elementHandle = javaElement.getHandleIdentifier();
				lastSelectedElement = javaElement;

			} else {
				structureKind = InteractionEvent.ID_UNKNOWN + ": " + selectedObject.getClass();
				if (selectedObject instanceof IAdaptable) {
					IResource resource = (IResource) ((IAdaptable) selectedObject).getAdapter(IResource.class);
					if (resource != null) {
						elementHandle = getHandleIdentifier(resource.getFullPath());
					}
				}
			}
		} else if (selection instanceof TextSelection && part instanceof JavaEditor) {
			TextSelection textSelection = (TextSelection) selection;
			IJavaElement javaElement;
			try {
				javaElement = SelectionConverter.resolveEnclosingElement((JavaEditor) part, textSelection);
				if (javaElement != null) {
					structureKind = STRUCTURE_KIND_JAVA;
					elementHandle = javaElement.getHandleIdentifier();
					if (javaElement.equals(lastSelectedElement)) {
						interactionKind = InteractionEvent.Kind.EDIT;
					}
					lastSelectedElement = javaElement;
				}
			} catch (JavaModelException e) {
				// ignore unresolved elements
			}
		} else if (part instanceof EditorPart) {
			EditorPart editorPart = (EditorPart) part;
			IEditorInput input = editorPart.getEditorInput();
			if (input instanceof IPathEditorInput) {
				structureKind = "file";
				elementHandle = getHandleIdentifier(((IPathEditorInput) input).getPath());
			}
		}

		// record the histories...
		IInteractionElement node = ContextCore.getContextManager().getElement(elementHandle);
		String delta = "";
		float selectionFactor = ContextCore.getCommonContextScaling().get(InteractionEvent.Kind.SELECTION);

		InteractionEvent event = new InteractionEvent(interactionKind, structureKind, elementHandle, part.getSite()
				.getId(), "null", delta, 0);

		MonitorUiPlugin.getDefault().notifyInteractionObserved(event);

		// add the interactions...
		IJavaElement element = JavaCore.create(elementHandle);
		if (element != null) {
			System.out.println("selectionChanged1: " + element.getElementName());
			Plugin.getDefault().getSelectionKeeper().addSelection(element); // salee
		}

		// adding the called methods or fields
		IJavaElement topElement = javaeditorutil.getJavaElement(part);
		if (topElement == null) {
			return;
		}

		if (selection instanceof TextSelection) {
			ITextSelection selectedtext = (ITextSelection) selection;
			if (selectedtext.getStartLine() < 1) {
				return;
			}

			IType topType = ((ITypeRoot) topElement).findPrimaryType();
			try {
				// do not work if the types of being deleted and created are the same & the type will not be permanent.
				IJavaElement locElement = ((ITypeRoot) topElement).getElementAt(selectedtext.getOffset());
				IJavaElement[] javaelements = ((ICodeAssist) topElement).codeSelect(selectedtext.getOffset(),
						selectedtext.getLength());

				if (locElement != null) {
					System.out.println("selectionChanged2: " + locElement.getElementName());
					Plugin.getDefault().getSelectionKeeper().addSelection(locElement); // salee
//					System.out.println("other element is: " + javaelements[0].getElementName());
				}

			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

	}

	// NOTE: duplicated from ResourceStructureBridge
	private String getHandleIdentifier(IPath path) {
		if (path != null) {
			return path.toPortableString();
		} else {
			return null;
		}
	}

	/**
	 * Some events do not have a valid handle, e.g. hande is null or ?
	 */
	public static boolean isValidStructureHandle(InteractionEvent event) {
		String handle = event.getStructureHandle();
		return handle != null && !handle.trim().equals("") && !handle.equals(SelectionMonitor.ID_JAVA_UNKNOWN)
				&& !handle.equals(SelectionMonitor.ID_JAVA_UNKNOW_OLD) && event.isValidStructureHandle();
	}
}
