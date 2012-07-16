package renewed.invertedindex;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class InvertedIndexer {

	private int numberOfDocuments;

	HashMap<String, SortedLinkedList<DocListNode>> hashmap = new HashMap<String, SortedLinkedList<DocListNode>> ();

	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}

	public void setNumberOfDocuments(int numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	public void increaseNumberOfDocuments() {
		this.numberOfDocuments = numberOfDocuments + 1;
	}

	public void decreaseNumberOfDocuments() {
		this.numberOfDocuments = numberOfDocuments - 1;
	}
	
	public void clear() {
		hashmap.clear();
		numberOfDocuments = 0;
	}

	public void insort(String word, Integer i, int count) {
		if (hashmap.containsKey(word)) {
//			System.out.println(word);
			SortedLinkedList<DocListNode> list = hashmap.get(word);
//			System.out.println(list.size());

			// num ++
			DocListNode docListNode = list.get(0);
			docListNode.setFrequencyInDoc(docListNode.getFrequencyInDoc() + 1);
			list.insort(new DocListNode(i, count));
			hashmap.put(word, list);
		}
		else {
			SortedLinkedList<DocListNode> list = new SortedLinkedList<DocListNode>();
			list.insort(new DocListNode(-1, 1));	
			list.insort(new DocListNode(i, count));			
			hashmap.put(word, list);					
		}
	}

	public double TFIDF_Similarity(Integer docID, String query) {
		double similarity;
		double AB = 0.0;
		double AA = 0.0;
		double BB = 0.0;

		String[] words = query.split(" ");	
		//		System.out.println(words.);
		for (int i = 0; i < words.length; i++) {
			double query_tfidf = tfidf(words[i]);		
			double doc_tfidf = tfidf(words[i], docID);
//			System.out.println("doc_tfidf " + doc_tfidf);

			AB = AB + query_tfidf * doc_tfidf;
			AA = AA + query_tfidf * query_tfidf;
			BB = BB + doc_tfidf * doc_tfidf;			
		}
		if (BB == 0) BB = 1.0;
//		System.out.println("AB " + AB);
//		System.out.println("AA " + AA);
//		System.out.println("BB " + BB);
		similarity = AB / (Math.sqrt(AA) * Math.sqrt(BB));

		return similarity;
	}

	public double TFIDF_Similarity(Integer docID, LinkedList<String> words) {
		double similarity;
		double AB = 0.0;
		double AA = 0.0;
		double BB = 0.0;

		for (int i = 0; i < words.size(); i++) {
			double query_tfidf = tfidf(words.get(i));		
			double doc_tfidf = tfidf(words.get(i), docID);
//			System.out.println("query_tfidf of " + words.get(i) + ": " + query_tfidf);
//			System.out.println("doc_tfidf " + doc_tfidf);

			AB = AB + query_tfidf * doc_tfidf;
			AA = AA + query_tfidf * query_tfidf;
			BB = BB + doc_tfidf * doc_tfidf;			
		}
		if (BB == 0) BB = 1.0;
		if (AA == 0) AA = 1.0;
//		System.out.println("AB " + AB);
//		System.out.println("AA " + AA);
//		System.out.println("BB " + BB);
		similarity = AB / (Math.sqrt(AA) * Math.sqrt(BB));

		return similarity;
	}

	public double tfidf(String term) {
		double tf = 1.0;
		double df = df(term);
		double idf;
		if (df == 0)
			idf = 0;
		else
			idf = Math.log(((numberOfDocuments)/df));
		return tf*idf;
	}

	public double tfidf(String term, Integer docId) {
		double tf = tf(term, docId);
		double df = df(term);
		double idf;
		if (df == 0)
			idf = 0;
		else
			idf = Math.log(((numberOfDocuments)/df));
		return tf*idf;
	}

	public double tf(String term, Integer docId) {
		SortedLinkedList<DocListNode> list = hashmap.get(term);
		if (list == null) return 0.0;

		for (DocListNode node: list) {
			if (node.getDocId() == docId) {
				int tf = node.getFrequencyInDoc();
				return tf;
			}
		}		
		return 0.0;		
	}

	public double df(String term) {
		SortedLinkedList<DocListNode> list = hashmap.get(term);
		if (list == null) return 0;
		
		return list.get(0).getFrequencyInDoc();
	}


	public SortedLinkedList<DocListNode> retrieve(LinkedList<String> words) {

		SortedLinkedList<DocListNode> answer = hashmap.get(words.get(0));
		//		print("", answer);
		for (int i = 1; i < words.size(); i++) {
			SortedLinkedList<DocListNode> next = hashmap.get(words.get(i));
			//			print("", next);

			SortedLinkedList<DocListNode> tmpAnswer = merge(answer, next);
			//			print("answer", tmpAnswer);	

			answer = tmpAnswer;
		}
		answer =  checkSimilarity(answer, words);
				
		return answer;
	}

	public SortedLinkedList<DocListNode> checkSimilarity(SortedLinkedList<DocListNode> answer, LinkedList<String> words) { // ¼öÁ¤
		if (answer == null) return null;

		for(Iterator<DocListNode> it=answer.iterator(); it.hasNext(); ) {
			DocListNode docListNode = it.next();
			
//			System.out.println("doc ID:" + docListNode.getDocId());
			if (docListNode.getDocId() == -1) {
				continue;
			}
			else {
				double similarity = TFIDF_Similarity(docListNode.getDocId(), words);
				docListNode.setSimilarity(similarity);
				if (similarity < 0.70) { // 0.90?
					it.remove();
				}
				else {
//					System.out.println("similarity: " + similarity);	
				}
			}
		} 
		
		Collections.sort(answer, new SimilarityComparator());
		
		int count = 0;
		for(Iterator<DocListNode> it=answer.iterator(); it.hasNext(); ) {
			DocListNode docListNode = it.next();
			
//			System.out.println("doc ID:" + docListNode.getDocId());
			if (docListNode.getDocId() == -1) {
				continue;
			}
			else {
				count++;
				double similarity = TFIDF_Similarity(docListNode.getDocId(), words);
				docListNode.setSimilarity(similarity);
				if (count > 3) { 
					it.remove();
				}
			}
		} 
		
//		print("retrieve answer", answer);
		
		return answer;		
	}

	public SortedLinkedList<DocListNode> merge(SortedLinkedList<DocListNode> p, SortedLinkedList<DocListNode> q) {
		if (p == null) {
			return null;
		}		
		if (q == null) {
			return p;
		}

		SortedLinkedList<DocListNode> answer = new SortedLinkedList<DocListNode>();

		for (int i = 0; i < p.size(); i++) {
			for (int j = 0; j < q.size(); j++) {
				if (p.get(i).getDocId() == q.get(j).getDocId()) {
					if (!contains(answer, p.get(i).getDocId()))
						answer.insort(p.get(i));
				}
			}
		}
		return answer;
	}

	boolean contains(SortedLinkedList<DocListNode> answer, Integer docID) {
		for (DocListNode node: answer) {
			if (node.getDocId() == docID)
				return true;
		}
		return false;
	}

	public void delete(Integer docNum2Delete) {
		for (String word: hashmap.keySet()) {
			SortedLinkedList<DocListNode> list = hashmap.get(word);

			for(Iterator<DocListNode> it=list.iterator(); it.hasNext(); ) {
				if(it.next().getDocId() == docNum2Delete) { 
					it.remove(); 					
					// num --
					DocListNode docListNode = list.get(0);
					docListNode.setFrequencyInDoc(docListNode.getFrequencyInDoc() - 1);
				}
			} 
		}
	}

	public void print() {
		System.out.println("#documents: " + numberOfDocuments);		
		for (String word: hashmap.keySet()) {
			System.out.print(word +":");
			SortedLinkedList<DocListNode> list = hashmap.get(word);

			for (DocListNode docNode: list) {
				System.out.print("[" + docNode.getDocId() +", " + docNode.getFrequencyInDoc() +"], ");
			}
			System.out.println();
		}
	}

	public void print(String title, SortedLinkedList<DocListNode> p) {
		if (p == null) return;
		System.out.print(title +": ");
		for (int i = 0; i < p.size(); i++) {	
			System.out.print(p.get(i) + ", ");	
		}
		System.out.println();
	}

}
