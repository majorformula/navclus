package old.salee.cs.similarity.indexers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import renewed.data.elements.ElementManager;


//import org.eclipse.jdt.core.String;
//import org.eclipse.mylyn.monitor.simulation.patterns.FrequentElement;
//import org.eclipse.mylyn.monitor.simulation.patterns.ElementManager;

import Jama.Matrix;

public class VectorGeneratorJava {

	// Element
	private final Map<Integer, String> elementIdValueMap = new HashMap<Integer, String>();

	// Title
	private final Map<Integer, Integer> documentIdNameMap = new HashMap<Integer, Integer>();

	// Matrix
	private Matrix matrix;

	public void generateVector(Map<Integer, ElementManager> documentElementFrequencyMap) {
		try {
//			Map<String, ElementManager> documentElementFrequencyMap = new HashMap<String, ElementManager>();
			HashSet<String> elementSet = new HashSet<String>();

			// create a Map of ids to docs from the documents	
			Integer docId = 0;
			for (Integer key : documentElementFrequencyMap.keySet()) {
				ElementManager document = documentElementFrequencyMap.get(key);
				elementSet.addAll(document.getElementSet());
				documentIdNameMap.put(docId, key);
				docId++;
			}

			// create a Map of ids to elements from the ElementSet			
			int elementId = 0;
			for (String element : elementSet) {
				elementIdValueMap.put(elementId, element);
				elementId++;
			}
			// we need a documents.keySet().size() x wordSet.size() matrix to hold
			// this info
			int numDocs = documentElementFrequencyMap.keySet().size();
			int numElements = elementSet.size();
			double[][] data = new double[numElements][numDocs];
			for (int i = 0; i < numElements; i++) {
				for (int j = 0; j < numDocs; j++) {
					// setting the doc name
					Integer docName = documentIdNameMap.get(j);
					ElementManager list = documentElementFrequencyMap.get(docName);
					// setting the element name
					String element = elementIdValueMap.get(i);
					int count = list.getCount(element);
					data[i][j] = count;
//					System.out.println(docName + " " + element.getElementName() + " " + count);
				}
			}
			matrix = new Matrix(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateVector() {
		Matrix matrix;
		matrix = new Matrix(2, 3);
		System.out.println("I test!");
	}

//	private int getCount(List<FrequentElement> list, String element) {
//		for (FrequentElement feelement : list) {
//			if (feelement.equals(element)) {
//				return feelement.getCount();
//			}
//		}
//		return 0;
//	}

	public void prettyPrintMatrix(String legend, Matrix matrix, String[] documentNames, String[] elements,
			PrintWriter writer) {
		writer.printf("=== %s ===%n", legend);
		writer.printf("%25s", " ");
		for (String documentName : documentNames) {
			writer.printf("%8s", documentName);
		}
		writer.println();
		for (int i = 0; i < elements.length; i++) {
			writer.printf("%25s", elements[i]);
			for (int j = 0; j < documentNames.length; j++) {
				writer.printf("%8.2f", matrix.get(i, j));
			}
			writer.println();
		}
		writer.flush();
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public Integer[] getDocumentNames() {
		Integer[] documentNames = new Integer[documentIdNameMap.keySet().size()];
		for (int i = 0; i < documentNames.length; i++) {
			documentNames[i] = documentIdNameMap.get(i);
		}
		return documentNames;
	}

	public String[] getElements() {
		String[] elements = new String[elementIdValueMap.keySet().size()];
		for (int i = 0; i < elements.length; i++) {
			String element = elementIdValueMap.get(i);
//			if (word.contains("|||")) {
//				// phrases are stored with length for other purposes, strip it off
//				// for this report.
//				word = word.substring(0, word.indexOf("|||"));
//			}
			elements[i] = element;
		}
		return elements;
	}

//	private boolean exist(String element) {
//		if (element == null) {
//			return false;
//		}
//
////		if (!element.exists()) {
////			return false;
////		}
//		return true;
//	}
}
