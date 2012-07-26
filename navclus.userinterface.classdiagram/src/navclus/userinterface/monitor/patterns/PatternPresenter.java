package navclus.userinterface.monitor.patterns;

import navclus.userinterface.classdiagram.NavClusView;
import navclus.userinterface.classdiagram.actions.RedrawAction;
import navclus.userinterface.classdiagram.java.analyzer.RootModel;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;

public class PatternPresenter {

	NavClusView viewer;

	RootModel rootmodel;

	public PatternPresenter() {
		this.viewer = NavClusView.getDefault(); // I change the 	
	}

	public void show(IJavaElement javaElement) {
		this.rootmodel = viewer.getRootModel();
		System.out.println(this.rootmodel);
		// clear the view first
//		this.clear();

		switch (javaElement.getElementType()) {
		case (IJavaElement.METHOD):
		case (IJavaElement.FIELD):
			// show the elements in a class figure
			rootmodel.addElement(javaElement);
			break;
		case (IJavaElement.TYPE):
			rootmodel.createType((IType) javaElement);
			break;
		}

		rootmodel.drawNodes();

		(new RedrawAction(viewer)).run();
	}

//	public void show(List<FrequentElement> selectedElements, List<FrequentLine> selectedLines, int threshold) {
//		// clear the view first
//		this.clear();
//
//		HashSet<IJavaElement> selectedJavaElement = new HashSet<IJavaElement>();
//
//		// draw the view second
//		for (FrequentElement element : selectedElements) {
//			if (element.getCount() < threshold) {
//				continue;
//			}
//			IJavaElement javaElement = element.getJavaElement();
//			selectedJavaElement.add(javaElement);
//
//			switch (javaElement.getElementType()) {
//			case (IJavaElement.METHOD):
//			case (IJavaElement.FIELD):
//				// show the elements in a class figure
//				rootmodel.addElement(javaElement);
//				break;
//			case (IJavaElement.TYPE):
//				rootmodel.createType((IType) javaElement);
//				break;
//			}
//		}
//		rootmodel.drawNodes();
//
//		for (FrequentLine line : selectedLines) {
////			if (line.getCount() < threshold) {
////				continue;
////			}
//
////			System.out.print("selected Line:");
////			line.print();
//
//			if (!selectedJavaElement.contains(line.getPreviousElement())) {
//				continue;
//			}
//
//			if (!selectedJavaElement.contains(line.getNextElement())) {
//				continue;
//			}
//
//			rootmodel.createNavigationalRelation((IType) line.getPreviousElement(), (IType) line.getNextElement());
//		}
//
//		(new RedrawAction(viewer)).run();
//	}

	public void clear() {
		rootmodel.clearModelinView();

		(new RedrawAction(viewer)).run();
	}
}
