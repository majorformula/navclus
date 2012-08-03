package navclus.recommendation.recommender;

import java.util.Set;

import renewed.data.elements.ElementManager;
import renewed.invertedindex.DocListNode;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.invertedindex.SortedLinkedList;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.recommender.StringQueue;


public class XMLSampleRecommender {
	
	public ElementManager recommend(StringQueue contextQueue, MacroClusterManager macroClusterManager) {
		
		SortedLinkedList<DocListNode> answer = (new XMLSampleRecommender()).retrieve(contextQueue, macroClusterManager.getMacroClusterIndexer());		
		if (answer == null) return null;
		
		ElementManager recommendation = new ElementManager();
		recommendation = macroClusterManager.retrieve(answer);
		recommendation.sort();
		return recommendation;
	}

	public SortedLinkedList<DocListNode> retrieve(StringQueue contextQueue, MacroClusterIndexer macroClusterIndexer) {
		SortedLinkedList<DocListNode> answer = macroClusterIndexer.retrieve(contextQueue.getStringQueue());
		print(contextQueue);
//		print("retrieve answer", answer);
		return answer;
	}
	
	public void print(String title, SortedLinkedList<DocListNode> p) {
		if (p == null) return;
		
		System.out.print(title +": ");
		for (int i = 0; i < p.size(); i++) {	
			System.out.print(p.get(i) + ", ");	
		}
		System.out.println();
	}
	
	public void print(StringQueue contextQueue) {
		System.out.print("Query: ");
		for (String e: contextQueue.toSet()) {
			System.out.print(e + ", ");
		}
		System.out.println();
	}

}