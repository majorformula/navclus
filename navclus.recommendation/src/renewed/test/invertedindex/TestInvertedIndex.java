package renewed.test.invertedindex;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;

import renewed.invertedindex.DocListNode;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.invertedindex.SortedLinkedList;


public class TestInvertedIndex {

	public static void main(String[] args) {
	/*	
		ArrayList<String> documentList = new ArrayList<String>();		
		documentList.add("one fish two fish");
		documentList.add("red fish blue fish");
		documentList.add("cat in the hat");
		documentList.add("green eggs and ham");
						
		for (int i = 0; i < documentList.size(); i++) {
			
			String sentence = documentList.get(i);
			String[] words = sentence.split(" ");
			
			for (String word: words) {
				indexer.insort(word, i);
			}
		}
	*/	
		MacroClusterIndexer indexer = new MacroClusterIndexer();
		indexer.insort("one", 0, 1);
		indexer.insort("fish", 0, 3);
		indexer.insort("two", 0, 1);		
		indexer.insort("red", 0, 1);
		indexer.increaseNumberOfDocuments();
		indexer.insort("red", 1, 2);
		indexer.insort("fish", 1, 3);
		indexer.insort("blue", 1, 1);
		indexer.insort("high", 1, 1);
		indexer.increaseNumberOfDocuments();
		indexer.insort("cat", 2, 1);
		indexer.insort("in", 2, 1);
		indexer.insort("the", 2, 1);
		indexer.insort("hat", 2, 1);
		indexer.increaseNumberOfDocuments();
		
		// retrieve ...
		LinkedList<String> words = new LinkedList<String>();
		words.add("red");
		words.add("fish");
		SortedLinkedList<DocListNode> answer = indexer.retrieve(words);	
		print("answer", answer);
				
		// recommend ...
		double similarity = indexer.TFIDF_Similarity(0, words);
		System.out.println(similarity);
		similarity = indexer.TFIDF_Similarity(1, "red fish");
		System.out.println(similarity);
		
//		System.out.println("-- Final --");
//		indexer.delete(1);
//		indexer.print();
	}
	
	public static void print(String title, SortedLinkedList<DocListNode> p) {

		System.out.print(title +": ");
		for (int i = 0; i < p.size(); i++) {	
			System.out.print(p.get(i).getDocId() + ", ");	
		}
		System.out.println();
	}
	
}