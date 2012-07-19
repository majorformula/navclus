/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package old.salee.cs.similarity;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import old.salee.cs.similarity.SearcherJava.SearchResult;
import old.salee.cs.similarity.indexers.IdfIndexerJava;
import old.salee.cs.similarity.indexers.LsiIndexerJava;
import old.salee.cs.similarity.indexers.TfIndexerJava;
import old.salee.cs.similarity.indexers.VectorGeneratorJava;
import renewed.data.elements.ElementManager;


//import org.eclipse.jdt.core.String;
//import org.eclipse.mylyn.monitor.simulation.patterns.FrequentElement;
//import org.eclipse.mylyn.monitor.simulation.patterns.ElementManager;

import Jama.Matrix;

public class VectorManager {

	private VectorGeneratorJava vectorGenerator;
	private Map<String, ElementManager> documents;
	private Map<String, String> documentStrings;

	public void createVector(HashMap<Integer, ElementManager> clusters) {
		try {
			vectorGenerator = new VectorGeneratorJava();
			vectorGenerator.generateVector(clusters);
//			vectorGenerator.prettyPrintMatrix("Occurences", vectorGenerator.getMatrix(),
//					vectorGenerator.getDocumentNames(), vectorGenerator.getElements(),
//					new PrintWriter(System.out, true));

			TfIndexerJava tfindexer = new TfIndexerJava();
			Matrix tfMatrix = tfindexer.transform(vectorGenerator.getMatrix());
//			vectorGenerator.prettyPrintMatrix("Term Frequency", tfMatrix, vectorGenerator.getDocumentNames(),
//					vectorGenerator.getElements(), new PrintWriter(System.out, true));
//			System.out.println("wow second! I am ready for creating Vector");

			IdfIndexerJava idfindexer = new IdfIndexerJava();
			Matrix idfMatrix = idfindexer.transform(vectorGenerator.getMatrix());
//			vectorGenerator.prettyPrintMatrix("Inverse Document Frequency", idfMatrix,
//					vectorGenerator.getDocumentNames(), vectorGenerator.getElements(),
//					new PrintWriter(System.out, true));
//			System.out.println("wow second! I am ready for creating Vector");

			LsiIndexerJava lsiindexer = new LsiIndexerJava();
			Matrix lsiMatrix = lsiindexer.transform(vectorGenerator.getMatrix());
//			vectorGenerator.prettyPrintMatrix("Latent Semantic (LSI)", lsiMatrix, vectorGenerator.getDocumentNames(),
//					vectorGenerator.getElements(), new PrintWriter(System.out, true));
//			System.out.println("wow second! I am ready for creating Vector");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Error happens!");
			e.printStackTrace();
		}
	}

	public List<SearchResult> compareSimilarity(LinkedList<String> queryElements) {
		try {
//			vectorGenerator = new VectorGeneratorJava();
//
//			Map<String, ElementManager> documents = new LinkedHashMap<String, ElementManager>();
////			documentStrings = new LinkedHashMap<String, String>();
////
//			for (ElementManager freqList : fragments) {
//				String title = freqList.getTitle();
//				documents.put(title, freqList);
//			}
//
//			vectorGenerator.generateVector(documents);
////			vectorGenerator.prettyPrintMatrix("Occurences", vectorGenerator.getMatrix(),
////					vectorGenerator.getDocumentNames(), vectorGenerator.getElements(),
////					new PrintWriter(System.out, true));
//
//			TfIndexerJava tfindexer = new TfIndexerJava();
//			Matrix tfMatrix = tfindexer.transform(vectorGenerator.getMatrix());
//			vectorGenerator.prettyPrintMatrix("Term Frequency", tfMatrix, vectorGenerator.getDocumentNames(),
//					vectorGenerator.getElements(), new PrintWriter(System.out, true));
//			System.out.println("wow second! I am ready for creating Vector");
//
//			IdfIndexerJava idfindexer = new IdfIndexerJava();
//			Matrix idfMatrix = idfindexer.transform(vectorGenerator.getMatrix());
//			vectorGenerator.prettyPrintMatrix("Inverse Document Frequency", idfMatrix,
//					vectorGenerator.getDocumentNames(), vectorGenerator.getElements(),
//					new PrintWriter(System.out, true));
//			System.out.println("wow second! I am ready for creating Vector");
//
//			LsiIndexerJava lsiindexer = new LsiIndexerJava();
//			Matrix lsiMatrix = lsiindexer.transform(vectorGenerator.getMatrix());
//			vectorGenerator.prettyPrintMatrix("Latent Semantic (LSI)", lsiMatrix, vectorGenerator.getDocumentNames(),
//					vectorGenerator.getElements(), new PrintWriter(System.out, true));
//			System.out.println("wow second! I am ready for creating Vector");

			return this.testSearchWithTfIdfVector(queryElements);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<SearchResult> compareSimilarity(ElementManager context) {
		try {
//			System.out.println(queryTerms);
			return this.testSearchWithTfIdfVector(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public List<SearchResult> compareSimilarity(String queryTerms) {
		try {
//			System.out.println(queryTerms);
			return this.testSearchWithTfIdfVector(queryTerms);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

//  @Test
//  public void testCosineSimilarityWithTfIdfVector() throws Exception {
//    IdfIndexer indexer = new IdfIndexer();
//    Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
//    CosineSimilaritySalee cosineSimilarity = new CosineSimilaritySalee();
//    Matrix similarity = cosineSimilarity.transform(termDocMatrix);
//    prettyPrintMatrix("Cosine Similarity (TF/IDF)", similarity, 
//      vectorGenerator.getDocumentNames(), new PrintWriter(System.out, true));
//  }
//  
//  @Test
//  public void testCosineSimilarityWithLsiVector() throws Exception {
//    LsiIndexer indexer = new LsiIndexer();
//    Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
//    CosineSimilaritySalee cosineSimilarity = new CosineSimilaritySalee();
//    Matrix similarity = cosineSimilarity.transform(termDocMatrix);
//    prettyPrintMatrix("Cosine Similarity (LSI)", similarity, 
//      vectorGenerator.getDocumentNames(), new PrintWriter(System.out, true));
//  }

	public List<SearchResult> testSearchWithTfIdfVector(LinkedList<String> queryElements) throws Exception {

		//Get a string of query
		String queryTerms = getQueryTerms(queryElements);

		// generate the term document matrix via the appropriate indexer
		IdfIndexerJava indexer = new IdfIndexerJava();
		Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
		// set up the query
		SearcherJava searcher = new SearcherJava();
		searcher.setDocuments(vectorGenerator.getDocumentNames());
		searcher.setTerms(vectorGenerator.getElements());
		searcher.setSimilarity(new CosineSimilarityJava());
		searcher.setTermDocumentMatrix(termDocMatrix);
		// run the query
		List<SearchResult> results = searcher.search(queryTerms);
		List<SearchResult> shortenedResults = prettyPrintResults(queryTerms, results);

		return shortenedResults;
	}

	public List<SearchResult> testSearchWithTfIdfVector(ElementManager context) throws Exception {

		// generate the term document matrix via the appropriate indexer
		IdfIndexerJava indexer = new IdfIndexerJava();
		Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
		// set up the query
		SearcherJava searcher = new SearcherJava();
		searcher.setDocuments(vectorGenerator.getDocumentNames());
		searcher.setTerms(vectorGenerator.getElements());
		searcher.setSimilarity(new CosineSimilarityJava());
		searcher.setTermDocumentMatrix(termDocMatrix);
		// run the query
		List<SearchResult> results = searcher.search(context);
		List<SearchResult> shortenedResults = prettyPrintResults(results);

		return shortenedResults;
	}
	
	public List<SearchResult> testSearchWithTfIdfVector(String queryTerms) throws Exception {

		// generate the term document matrix via the appropriate indexer
		IdfIndexerJava indexer = new IdfIndexerJava();
		Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
		// set up the query
		SearcherJava searcher = new SearcherJava();
		searcher.setDocuments(vectorGenerator.getDocumentNames());
		searcher.setTerms(vectorGenerator.getElements());
		searcher.setSimilarity(new CosineSimilarityJava());
		searcher.setTermDocumentMatrix(termDocMatrix);
		// run the query
		List<SearchResult> results = searcher.search(queryTerms);
		List<SearchResult> shortenedResults = prettyPrintResults(queryTerms, results);

		return shortenedResults;
	}

	private String getQueryTerms(LinkedList<String> queryElements) {
		String queryTerms;

		queryTerms = "";
		for (String element : queryElements) {
			queryTerms = queryTerms + element + ", ";
		}

		return queryTerms;
	}

	public List<SearchResult> testSearchWithTfIdfVector() throws Exception {
		// generate the term document matrix via the appropriate indexer
		IdfIndexerJava indexer = new IdfIndexerJava();
		Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
		// set up the query
		SearcherJava searcher = new SearcherJava();
		searcher.setDocuments(vectorGenerator.getDocumentNames());
		searcher.setTerms(vectorGenerator.getElements());
		searcher.setSimilarity(new CosineSimilarityJava());
		searcher.setTermDocumentMatrix(termDocMatrix);
		// run the query
		List<SearchResult> results = searcher.search("handles RelativeLocator");
		prettyPrintResults("DeleteCommand DuplicateCommand", results);

		return results;
	}

//  @Test
//  public void testSearchWithLsiVector() throws Exception {
//    // generate the term document matrix via the appropriate indexer
//    LsiIndexer indexer = new LsiIndexer();
//    Matrix termDocMatrix = indexer.transform(vectorGenerator.getMatrix());
//    // set up the query
//    SearcherSalee searcher = new SearcherSalee();
//    searcher.setDocuments(vectorGenerator.getDocumentNames());
//    searcher.setTerms(vectorGenerator.getWords());
//    searcher.setSimilarity(new CosineSimilaritySalee());
//    searcher.setTermDocumentMatrix(termDocMatrix);
//    // run the query
//    List<SearchResult> results = 
//      searcher.search("DrawApplication createArrowMenu");
//    prettyPrintResults("DrawApplication createArrowMenu", results);
//  }

	private void prettyPrintMatrix(String legend, Matrix matrix, String[] documentNames, PrintWriter writer) {
		writer.printf("=== %s ===%n", legend);
		writer.printf("%6s", " ");
		for (String documentName : documentNames) {
			writer.printf("%8s", documentName);
		}
		writer.println();
		for (int i = 0; i < documentNames.length; i++) {
			writer.printf("%6s", documentNames[i]);
			for (int j = 0; j < documentNames.length; j++) {
				writer.printf("%8.4f", matrix.get(i, j));
			}
			writer.println();
		}
		writer.flush();
	}

	private List<SearchResult> prettyPrintResults(String query, List<SearchResult> results) throws Exception {
		List<SearchResult> shortenedResults = new ArrayList<SearchResult>();

		System.out.printf("Results for query: [%s]%n", query);
		int i = 1;
		for (SearchResult result : results) {
			i++;
//			System.out.printf("%s (score = %8.4f) ", result.title, result.score);
//			String text = documentStrings.get(result.title);
//			System.out.println(text);

//			System.out.println();

			shortenedResults.add(result);
			if (i > 1) { // #numbers
				break;
			}
		}
		return shortenedResults;
	}
	
	private List<SearchResult> prettyPrintResults(List<SearchResult> results) throws Exception {
		List<SearchResult> shortenedResults = new ArrayList<SearchResult>();

//		System.out.printf("Results for query: [%s]%n", query);
		int i = 1;
		for (SearchResult result : results) {
			i++;
//			System.out.printf("%s (score = %8.4f) ", result.title, result.score);
//			String text = documentStrings.get(result.title);
//			System.out.println(text);

//			System.out.println();

			shortenedResults.add(result);
//			if (i > 3) { // #numbers
//				break;
//			}
		}
		return shortenedResults;
	}

}
