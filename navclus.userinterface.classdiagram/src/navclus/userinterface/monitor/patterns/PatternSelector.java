package navclus.userinterface.monitor.patterns;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.eclipse.jdt.core.IJavaElement;

/**
 * PatternSelect is a primary class which reads several interaction traces, creates patterns, print the navigation
 * patterns.
 * 
 * Error: I think this class takes a pipe-line architecture of transfering the pattern from XMLReader through
 * SegmentManager, FragmentManager
 * 
 * @author Seonah Lee, saleese@gmail.com
 */
public class PatternSelector {

	private static PatternSelector singleton;

	public static PatternSelector patternSelector() {

		if (singleton == null) {
			singleton = new PatternSelector();
		}

		return singleton;
	}

//	private final XMLReader xmlReader;
//
//	private final SegmentManager segmentManager;

//	private final FragmentManager fragmentManager;

//	private final VectorManager vectorManager;

	private final PatternPresenter patternPresenter;

	public PatternPresenter getPatternPresenter() {
		return patternPresenter;
	}

	private final int threshold = 3;

	public PatternSelector() {

//		// TODO I did initialize the xml file, because it is final in the class..
//		this.xmlReader = new XMLReader();
//		this.segmentManager = new SegmentManager();
//		this.fragmentManager = new FragmentManager();
		this.patternPresenter = new PatternPresenter();
//		this.vectorManager = new VectorManager();
	}

	public void initiate() throws FileNotFoundException {

		// TODO I did to flexibly change the path for an installation
		String sPath = this.setUpDataPath();

//		// transform xml files to segments.
//		for (int i = 1; i <= 5; i++) {
//			XMLReader subXMLReader = new XMLReader(sPath + "successfulone" + i + "-history.xml");
//			subXMLReader.run();
//
//			SegmentManager subSegmentManager = subXMLReader.getSegmentManager(); //segmentmanager.copy();
//			segmentManager.add(subSegmentManager.copy());
//		}
//
//		// test segments...
////		segmentManager.print();
//		segmentManager.calculateStatistics();

		// transform segments to fragments & test them
//		fragmentManager.transferSegments2FrequentLists(segmentManager.getSegmentList());
//		fragmentManager.sortEachList();
//		fragmentManager.sort();
//		fragmentManager.weightbyProgrammers();
//		fragmentManager.print();

//		// Extract Frequent Items
//		fragmentManager.extractFrequentItems(segmentManager.getSegmentList(), threshold);
////		fragmentManager.printFrequentItems();
//		fragmentManager.markFrequentItems();

		// transform fragments to a vector
//		vectorManager.createVector(fragmentManager.getFragments(), fragmentManager.getFrequentItems());

//		fragmentManager.copyPoiList(threshold);
//		fragmentManager.printPoiList(threshold);
//
//		// Optimize points of interest
//		fragmentManager.optimizePointsOfInterest();
//		fragmentManager.printOptimizedPoiList();
	}

	private String setUpDataPath() {
		// TODO I did to flexibly change the path for an installation
		File dummy = new File("");
		return dummy.getAbsolutePath() + "\\workspace\\ca.ubc.cs.salee.monitor.usecontext\\data\\";
	}

//	public boolean IsFrequent(IJavaElement javaElement) {
//		if (fragmentManager.IsFrequent(javaElement)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * select the list of points of interest with the triggers
	 * 
	 * @see SelectionKeepter.addSelection() call this method!
	 */
	public void showSelectedPatterns(LinkedList<IJavaElement> triggers) {
		try {
//			// print patterns...
//			fragmentManager.sort(triggers, threshold);
//			fragmentManager.printPointOfInterest(triggers);

			// ** show frequent lines here!!! **
			System.out.println("##### Selection #####");
//			List<SearchResult> results = vectorManager.compareSimilarity(triggers);
//			FrequentList selectedList = fragmentManager.findFragments(results);

			// show patterns
//			showPointofInterest(selectedList, threshold);

		} catch (Exception e) {
			System.err.println("Error in PatternSelector.showSelectedPatterns():" + e.getMessage());
			e.printStackTrace();
		}
	}

//	private void showPointofInterest(FrequentList selectedList, int threshold) {
////		List<FrequentElement> selectedElements = selectedList.getFrequentElementList();
//		List<FrequentLine> selectedLines = selectedList.getLineList();
//
////		patternPresenter.show(selectedElements, selectedLines, threshold);
//	}
//
//	public void clearView() {
//		patternPresenter.clear();
//	}

//	private void printTriggerList(LinkedList<IJavaElement> triggerList) {
//	System.out.print("Use Context: {");
//	for (IJavaElement element : triggerList) {
//		System.out.print(element.getElementName() + " ");
//	}
//	System.out.println();
//}

}