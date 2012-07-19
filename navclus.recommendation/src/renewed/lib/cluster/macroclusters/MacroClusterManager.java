package renewed.lib.cluster.macroclusters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;
import renewed.invertedindex.DocListNode;
import renewed.invertedindex.InvertedIndexer;
import renewed.invertedindex.SortedLinkedList;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.microclusters.MicroVector;
import renewed.lib.cluster.similaritymatrix.CosineSimilarityCalculator;

public class MacroClusterManager {
	
	HashMap<Integer, ElementManager> macroHashmap = new HashMap<Integer, ElementManager> ();
	ElementManager newMacroManager ;
	
	Vector<ElementManager> v = new Vector<ElementManager>();

	public void clear() {
		macroHashmap.clear();
	}

	public void clearStatus() {			
		Set<Integer> keyStrings = macroHashmap.keySet();
		for (Integer keyString: keyStrings) {
			ElementManager elementManager = macroHashmap.get(keyString);
			elementManager.cleared();
		}
	}

	public void deletefromIndex(Integer parentKey, InvertedIndexer macroClusterIndexer) {
		macroClusterIndexer.delete(parentKey);
		macroClusterIndexer.decreaseNumberOfDocuments(); // #doc --
	}

	public ElementManager get(Object arg0) {
		return macroHashmap.get(arg0);
	}

	public HashMap<Integer, ElementManager> getHashmap() {
		return macroHashmap;
	}
	
	public void insort2Index(Integer parentKey, ElementManager maxManager, InvertedIndexer macroClusterIndexer) {
		for (Element element: maxManager.getVector()) {
			macroClusterIndexer.insort(element.getName(), parentKey, element.getCount());
		}
		macroClusterIndexer.increaseNumberOfDocuments(); // #doc ++
	}

	public Set<Integer> keySet() {
		return macroHashmap.keySet();
	}

	public void printClusters() {

		int i = 0;
		Set<Integer> keyStrings = macroHashmap.keySet();
		for (Integer keyString: keyStrings) {
			ElementManager elementManager = macroHashmap.get(keyString);
			if (elementManager.getChildren() >= 2) {
				System.out.println("Cluster:" + i++ + "#micro-clusters:" + elementManager.getChildren());
				elementManager.sort();
				elementManager.println(3);
			}
		}
	}

	public void printCount() {
		int count2 = 0;
		int count1 = 0;
		Set<Integer> keyStrings = macroHashmap.keySet();
		for (Integer keyString: keyStrings) {
			ElementManager elementManager = macroHashmap.get(keyString);
			if (elementManager.getChildren() >= 2) {
				count2++;
			}
			else if  (elementManager.getChildren() >= 1) {
				count1++;
			}

		}
		System.out.println(count2);
		//		System.out.println(count1);
	}

	public void printSummary() {

		int none = 0;
		int modified = 0;
		int inserted = 0;		
		Set<Integer> keyStrings = macroHashmap.keySet();
		for (Integer keyString: keyStrings) {
			ElementManager elementManager = macroHashmap.get(keyString);
			int updated = elementManager.getUpdated();
			switch (updated) {
			case 0:
				none++;
				break;
			case 1:
				//				System.out.println("modified: " + elementManager.size() +": " + keyString);
				modified++;
				break;
			case 2:
				//				System.out.println("inserted --" + elementManager.size() +": " + keyString);	
				inserted++;
				break;
			default:
				System.out.println("error");
			}

			////		elementManager.println(iThreshold);
		}
		System.out.print("#macro-clusters:" + macroHashmap.size() +",	");		
		System.out.print("#nochange:" + none + ",	");
		System.out.print("#modified:" + modified + ",	");
		System.out.println("#inserted:" + inserted);
		//		this.printIndex();			
	}

	public ElementManager retrieve(SortedLinkedList<DocListNode> answer) {
		ElementManager recommendManager = new ElementManager();
		for (DocListNode i: answer) {
			ElementManager elementManager = macroHashmap.get(i.getDocId());
			recommendManager.merge(elementManager);
		}
		return recommendManager;
	}

	public int size() {
		return macroHashmap.size();
	}
		
	public InvertedIndexer create(MicroClusterManager microClusterManager, MicroVector microVector, InvertedIndexer macroClusterIndexer) {
		for (int j = 0; j < microVector.getGroupVector().size(); j++) {
//			System.out.println("Group: " + j);
			newMacroManager = new ElementManager();
			Set<String> groupSet = microVector.getGroupVector().get(j);
			for (String key: groupSet) {
//				System.out.println(key);
				ElementManager microManager = microClusterManager.get(key);
				newMacroManager.merge(microManager);			
			}
//			System.out.println();
			macroHashmap.put(j, newMacroManager);
			insort2Index(j, newMacroManager, macroClusterIndexer);
			// insert no indexer
		}		
		return macroClusterIndexer;		
	}

	public InvertedIndexer update(MicroClusterManager microClusterManager, double threshold, InvertedIndexer macroClusterIndexer) {
		
		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();

		v.addAll(microClusterManager.values()); // v ...
		Collections.sort(v, new SizeComparator());
		
//		Set<String> microKeySet = microClusterManager.keySet();		
//		for (String microKey: microKeySet) {
//			ElementManager microManager = microClusterManager.get(microKey);
		for (ElementManager microManager: v)	{		
//			System.out.println(microManager.size());
			// no compute in this case: 0 = none, 1 = modified, 2 = inserted
			// in case of "no change..."
				double maxOverlap = 0;
				Integer maxKey = -1;			
				Set<Integer> macroKeySet = macroHashmap.keySet();		
				for (Integer macroKey: macroKeySet) {
					ElementManager macroManager = macroHashmap.get(macroKey);

					// compute the similarity
					if (macroManager == null) System.out.println(macroKey);
					double value = calculator.CosineSimilarity(microManager, macroManager);

					if (value > maxOverlap) {
						maxOverlap = value;
						maxKey = macroKey;
					}
				}			

				Integer parentKey = -1;
				if (maxOverlap >= threshold) {
					// update: merge this with existing ones...
					if (maxKey == -1) continue;
					ElementManager maxManager = macroHashmap.get(maxKey);								
					maxManager.merge(microManager);

					maxManager.addChild();
					parentKey = maxKey;

					maxManager.modified();
					macroHashmap.put(parentKey, maxManager);

					// update an indexer
					if (maxManager.getChildren() == 2) {
						// 새 자료 추가
						insort2Index(maxKey, maxManager, macroClusterIndexer);
					}
					else if (maxManager.getChildren() > 2) {
						// 기존 자료 삭제
						deletefromIndex(maxKey, macroClusterIndexer);
						// 새 자료 추가
						insort2Index(maxKey, maxManager, macroClusterIndexer);
					}
				}
				else {
					// insert: create a new one...
					newMacroManager = new ElementManager();
					newMacroManager.merge(microManager);

					newMacroManager.addChild();								
					parentKey = macroHashmap.size() + 1;

					newMacroManager.inserted();
					macroHashmap.put(parentKey, newMacroManager);
					// insert no indexer
				}

				microManager.setParentKey(parentKey);					
		}
		v.clear();
		return macroClusterIndexer;
	}
	
}
