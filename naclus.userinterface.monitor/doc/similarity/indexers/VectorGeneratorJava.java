package salee.cs.similarity.indexers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.mylyn.monitor.simulation.patterns.FrequentElement;
import org.eclipse.mylyn.monitor.simulation.patterns.FrequentList;

import Jama.Matrix;

public class VectorGeneratorJava {

	// Element
	private final Map<Integer, IJavaElement> elementIdValueMap = new HashMap<Integer, IJavaElement>();

	// Title
	private final Map<Integer, String> documentIdNameMap = new HashMap<Integer, String>();

	// Matrix
	private Matrix matrix;

	public void generateVector(Map<String, FrequentList> documentElementFrequencyMap) {
		try {
//			Map<String, FrequentList> documentElementFrequencyMap = new HashMap<String, FrequentList>();
			HashSet<IJavaElement> elementSet = new HashSet<IJavaElement>();

			// create a Map of ids to docs from the documents	
			Integer docId = 0;
			for (String key : documentElementFrequencyMap.keySet()) {
				FrequentList list = documentElementFrequencyMap.get(key);
				elementSet.addAll(list.getJavaElementSet());
				documentIdNameMap.put(docId, key);
				docId++;
			}

			// create a Map of ids to elements from the ElementSet			
			int elementId = 0;
			for (IJavaElement element : elementSet) {
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
					String docName = documentIdNameMap.get(j);
					FrequentList list = documentElementFrequencyMap.get(docName);
					// setting the element name
					IJavaElement element = elementIdValueMap.get(i);
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

	private int getCount(List<FrequentElement> list, IJavaElement element) {
		for (FrequentElement feelement : list) {
			if (feelement.equals(element)) {
				return feelement.getCount();
			}
		}
		return 0;
	}

	public void prettyPrintMatrix(String legend, Matrix matrix, String[] documentNames, IJavaElement[] elements,
			PrintWriter writer) {
		writer.printf("=== %s ===%n", legend);
		writer.printf("%25s", " ");
		for (String documentName : documentNames) {
			writer.printf("%8s", documentName);
		}
		writer.println();
		for (int i = 0; i < elements.length; i++) {
			writer.printf("%25s", elements[i].getElementName());
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

	public String[] getDocumentNames() {
		String[] documentNames = new String[documentIdNameMap.keySet().size()];
		for (int i = 0; i < documentNames.length; i++) {
			documentNames[i] = documentIdNameMap.get(i);
		}
		return documentNames;
	}

	public IJavaElement[] getElements() {
		IJavaElement[] elements = new IJavaElement[elementIdValueMap.keySet().size()];
		for (int i = 0; i < elements.length; i++) {
			IJavaElement element = elementIdValueMap.get(i);
//			if (word.contains("|||")) {
//				// phrases are stored with length for other purposes, strip it off
//				// for this report.
//				word = word.substring(0, word.indexOf("|||"));
//			}
			elements[i] = element;
		}
		return elements;
	}

	private boolean exist(IJavaElement element) {
		if (element == null) {
			return false;
		}

		if (!element.exists()) {
			return false;
		}
		return true;
	}
}
