package renewed.lib.recommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import old.salee.cs.similarity.SearcherJava.SearchResult;
import old.salee.cs.similarity.VectorManager;

import renewed.data.elements.ElementManager;
import renewed.data.queue.EventQueue;
import renewed.evaluation.answer.AnswerSheet;
import renewed.evaluation.answer.EditQueue;
import renewed.evaluation.answer.EditRecord;
import renewed.evaluation.answer.Result;
import renewed.evaluation.answer.SetOperator;
import renewed.evaluation.answer.TotalResults;
import renewed.in.reader.StructureHandlePrinter;
import renewed.invertedindex.DocListNode;
import renewed.invertedindex.InvertedIndexer;
import renewed.invertedindex.SortedLinkedList;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.cluster.similaritymatrix.CosineSimilarityCalculator;

public class NavClusTxTRecommender {

	public void recommend(String directory, 
			String file, 
			InvertedIndexer macroClusterIndexer, 
			MacroClusterManager macroClusterManager, 
			TotalResults totalResults) {		

		StructureHandlePrinter shp = new StructureHandlePrinter();
		ElementManager query = new ElementManager();
		EventQueue queryQueue = new EventQueue(3);
		ElementManager recommendation = new ElementManager();

		// answer sheet
		AnswerSheet answerSheet = new AnswerSheet();
		EditQueue editQueue = answerSheet.answer(directory, file);
		SetOperator setOperator = new SetOperator();

		try {
			BufferedReader in = new BufferedReader(new FileReader(directory + "/" + file));
			String strLine;

			String lastDate = "";
			while ((strLine = in.readLine()) != null) {
				String[] strArray = strLine.split("/");
				queryQueue.update(strArray[3].trim(), strArray[0].trim());
//				query.insert(strArray[3].trim());
				lastDate = strArray[1].trim();

				// make a recommendation
				if ((queryQueue.size() >= 3) && (queryQueue.isUpdate())) {
					query.addAll(queryQueue.toStringSet());
					SortedLinkedList<DocListNode> answer = retrieve(query, macroClusterIndexer, macroClusterManager);
					
					if (answer == null) continue;
					recommendation = macroClusterManager.retrieve(answer);

					Set<String> recommendationSet, answerSet = new TreeSet<String>();
					
					//---------- 여기서 부터 계속 고치기 ---------------------------------------//
					if (recommendation.size() > 0) {
						recommendation.sort();					
						recommendationSet = recommendation.getRecommendationSet(1, 10);
						System.out.println(recommendationSet.size());
						//						result.println(1, 10);

						// evaluation
						Iterator iterator2Recommend = editQueue.iterator();
						while (iterator2Recommend.hasNext()) {
							EditRecord editRecord = (EditRecord) iterator2Recommend.next();
							answerSet.add(editRecord.getElement());
						}

						// #answers > 0
						if ((answerSet.size() > 0) && (recommendationSet.size() > 0)) {
							double precision = setOperator.count(setOperator.intersection(recommendationSet, answerSet)) 
									/ (double) setOperator.count(recommendationSet);
							//							if (Double.isNaN(precision)) System.out.println("NaN");

							double recall = setOperator.count(setOperator.intersection(recommendationSet, answerSet)) 
									/ (double) setOperator.count(answerSet);

//							System.out.print("Precision:	" + precision + "	:	");						
//							System.out.println("Recall:	" + recall);

							totalResults.add(new Result(precision, recall));
						}
					}
					query.clear();
					recommendation.clear();
				}
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public SortedLinkedList<DocListNode> retrieve(ElementManager query, InvertedIndexer macroClusterIndexer, MacroClusterManager macroClusterManager) {
		LinkedList<String> words = new LinkedList<String>();		
		for (String word: query.getElementSet()) {
			words.add(word);
		}
		
		SortedLinkedList<DocListNode> answer = macroClusterIndexer.retrieve(words);
		return answer;
	}
}
