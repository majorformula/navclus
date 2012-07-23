package salee.cs.similarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IJavaElement;

import Jama.Matrix;

public class SearcherJava {

	public class SearchResult {
		public String title;

		public double score;

		public SearchResult(String title, double score) {
			this.title = title;
			this.score = score;
		}
	};

	private Matrix termDocumentMatrix;

	private List<String> documents;

	private List<IJavaElement> elements;

	private AbstractSimilarityJava similarity;

	public void setTermDocumentMatrix(Matrix termDocumentMatrix) {
		this.termDocumentMatrix = termDocumentMatrix;
	}

	public void setDocuments(String[] documents) {
		this.documents = Arrays.asList(documents);
	}

	public void setTerms(IJavaElement[] terms) {
		this.elements = Arrays.asList(terms);
	}

	public void setSimilarity(AbstractSimilarityJava similarity) {
		this.similarity = similarity;
	}

	public List<SearchResult> search(String query) {
		// build up query matrix
		Matrix queryMatrix = getQueryMatrix(query);
		final Map<String, Double> similarityMap = new HashMap<String, Double>();
		for (int i = 0; i < termDocumentMatrix.getColumnDimension(); i++) {
			double sim = similarity.computeSimilarity(queryMatrix, termDocumentMatrix.getMatrix(0,
					termDocumentMatrix.getRowDimension() - 1, i, i));
			if (sim > 0.1D) {
				similarityMap.put(documents.get(i), sim);
			}
		}
		return sortByScore(similarityMap);
	}

	private Matrix getQueryMatrix(String query) {
		Matrix queryMatrix = new Matrix(elements.size(), 1, 0.0D);
		String[] queryTerms = query.split("\\s+");
		for (String queryTerm : queryTerms) {
			int termIdx = 0;
			for (IJavaElement element : elements) {
				if (queryTerm.equalsIgnoreCase(element.getElementName())) {
					queryMatrix.set(termIdx, 0, 1.0D);
				}
				termIdx++;
			}
		}
		queryMatrix = queryMatrix.times(1 / queryMatrix.norm1());
		return queryMatrix;
	}

	private List<SearchResult> sortByScore(final Map<String, Double> similarityMap) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<String> docNames = new ArrayList<String>();
		docNames.addAll(similarityMap.keySet());
		Collections.sort(docNames, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return similarityMap.get(s2).compareTo(similarityMap.get(s1));
			}
		});
		for (String docName : docNames) {
			double score = similarityMap.get(docName);
			if (score < 0.00001D) {
				continue;
			}
			results.add(new SearchResult(docName, score));
		}
		return results;
	}
}
