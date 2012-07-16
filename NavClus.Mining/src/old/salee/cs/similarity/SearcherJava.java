package old.salee.cs.similarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;


//import org.eclipse.jdt.core.String;

import Jama.Matrix;

public class SearcherJava {

	public class SearchResult {
		public Integer title;

		public double score;

		public SearchResult(Integer title, double score) {
			this.title = title;
			this.score = score;
		}
	};

	private Matrix termDocumentMatrix;

	private List<Integer> documents;

	private List<String> elements;

	private AbstractSimilarityJava similarity;

	public void setTermDocumentMatrix(Matrix termDocumentMatrix) {
		this.termDocumentMatrix = termDocumentMatrix;
	}

	public void setDocuments(Integer[] documents) {
		this.documents = Arrays.asList(documents);
	}

	public void setTerms(String[] terms) {
		this.elements = Arrays.asList(terms);
	}

	public void setSimilarity(AbstractSimilarityJava similarity) {
		this.similarity = similarity;
	}
	
	public List<SearchResult> search(ElementManager context) {
		// build up query matrix
		Matrix queryMatrix = getQueryMatrix(context);
		final Map<Integer, Double> similarityMap = new HashMap<Integer, Double>();
		for (int i = 0; i < termDocumentMatrix.getColumnDimension(); i++) {
			double sim = similarity.computeSimilarity(queryMatrix, termDocumentMatrix.getMatrix(0,
					termDocumentMatrix.getRowDimension() - 1, i, i));
			if (sim > 0.1D) {
				similarityMap.put(documents.get(i), sim);
			}
		}
		return sortByScore(similarityMap);
	}

	public List<SearchResult> search(String query) {
		// build up query matrix
		Matrix queryMatrix = getQueryMatrix(query);
		final Map<Integer, Double> similarityMap = new HashMap<Integer, Double>();
		for (int i = 0; i < termDocumentMatrix.getColumnDimension(); i++) {
			double sim = similarity.computeSimilarity(queryMatrix, termDocumentMatrix.getMatrix(0,
					termDocumentMatrix.getRowDimension() - 1, i, i));
			if (sim > 0.1D) {
				similarityMap.put(documents.get(i), sim);
			}
		}
		return sortByScore(similarityMap);
	}

	private Matrix getQueryMatrix(ElementManager context) {
		Matrix queryMatrix = new Matrix(elements.size(), 1, 0.0D);
//		String[] queryTerms = query.split(";"); //\\s+");
		for (Element context_element : context.getVector()) {
//			queryTerm = queryTerm.trim();
			
			int termIdx = 0;
			for (String element : elements) {
				if (context_element.getName().equalsIgnoreCase(element)) {
					queryMatrix.set(termIdx, 0, (double) context_element.getCount() );
				}
				termIdx++;
			}
		}
		queryMatrix = queryMatrix.times(1 / queryMatrix.norm1());
		return queryMatrix;
	}
	
	private Matrix getQueryMatrix(String query) {
		Matrix queryMatrix = new Matrix(elements.size(), 1, 0.0D);
		String[] queryTerms = query.split(";"); //\\s+");
		for (String queryTerm : queryTerms) {
			queryTerm = queryTerm.trim();
			
			int termIdx = 0;
			for (String element : elements) {
				if (queryTerm.equalsIgnoreCase(element)) {
					queryMatrix.set(termIdx, 0, 1.0D);
				}
				termIdx++;
			}
		}
		queryMatrix = queryMatrix.times(1 / queryMatrix.norm1());
		return queryMatrix;
	}

	private List<SearchResult> sortByScore(final Map<Integer, Double> similarityMap) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<Integer> docNames = new ArrayList<Integer>();
		docNames.addAll(similarityMap.keySet());
		Collections.sort(docNames, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return similarityMap.get(s2).compareTo(similarityMap.get(s1));
			}
		});
		for (Integer docName : docNames) {
			double score = similarityMap.get(docName);
			if (score < 0.00001D) {
				continue;
			}
			results.add(new SearchResult(docName, score));
		}
		return results;
	}
}
