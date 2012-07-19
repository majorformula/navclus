package renewed.lib.cluster.microclusters;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.collections15.list.TreeList;

import renewed.data.elements.ElementManager;
import renewed.lib.cluster.similaritymatrix.CosineSimilarityCalculator;

public class MicroVector {
	
	Vector<MicroPair> microVector = new Vector<MicroPair>();
	
	Vector<Set<String>> groupVector = new Vector<Set<String>>();
	
	
	public Vector<MicroPair> getMicroVector() {
		return microVector;
	}

	public void setMicroVector(Vector<MicroPair> microVector) {
		this.microVector = microVector;
	}

	public Vector<Set<String>> getGroupVector() {
		return groupVector;
	}

	public void setGroupVector(Vector<Set<String>> groupVector) {
		this.groupVector = groupVector;
	}

	
	public void createClosestPair(MicroClusterManager microClusterManager) {
		
		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
		
		Set<String> keys = microClusterManager.getHashmap().keySet();
		for (String key1: keys) {
			ElementManager microManager1 = microClusterManager.getHashmap().get(key1);			
			double maxSimilarity = 0;
			String maxKey = null;	
			
			for (String key2: keys) {
				ElementManager microManager2 = microClusterManager.getHashmap().get(key2);				
				if (!key1.equals(key2)) {
					double similarity = calculator.CosineSimilarity(microManager1, microManager2);
					
					if (similarity > maxSimilarity) {
						maxSimilarity = similarity;
						maxKey = key2;
					}
				}				
			}
			
			if (maxKey != null)
				microVector.add(new MicroPair(key1, maxKey, maxSimilarity));
		}				
	}
	
	public void createClosestPair(MicroClusterManager microClusterManager, int nFreq) {
		
		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
		
		Set<String> keys = microClusterManager.getHashmap().keySet();
		for (String key1: keys) {
			ElementManager microManager1 = microClusterManager.getHashmap().get(key1);	
			int key1Count = microManager1.getCount(key1);
			if (key1Count < nFreq)
				continue;
			
			double maxSimilarity = 0;
			String maxKey = null;				
			for (String key2: keys) {
				ElementManager microManager2 = microClusterManager.getHashmap().get(key2);
				int key2Count = microManager1.getCount(key2);
				if (key2Count < nFreq)
					continue;								
				
				if (!key1.equals(key2)) {
					double similarity = calculator.CosineSimilarity(microManager1, microManager2);
					
					if (similarity > maxSimilarity) {
						maxSimilarity = similarity;
						maxKey = key2;
					}
				}				
			}
			
			if (maxKey != null)
				microVector.add(new MicroPair(key1, maxKey, maxSimilarity));
		}				
	}
	
	
	public void group() {
		
		Set<String> tmpSet = new TreeSet<String>();
				
		for (int i = 0; i < microVector.size(); i++) {
			MicroPair pair = microVector.get(i);			
			int gIndex = -1;
			double gSimilarity = -1;
			for (int j = 0; j < groupVector.size(); j++) {
				Set<String> groupSet = groupVector.get(j);
				if (groupSet.contains(pair.getKey2())) {
					gIndex = j;
					gSimilarity = pair.getSimilarity();
					break;
				}
			}
			
			if ((gIndex != -1) && (gSimilarity > 0.2)) {
				Set<String> groupSet = groupVector.get(gIndex);
				groupSet.add(pair.getKey1());
			}
			else {
				Set<String> groupSet = new TreeSet<String>();
//				System.out.println(pair.getKey1());
//				System.out.println(pair.getKey2());
//				System.out.println(pair.getSimilarity());
				
				groupSet.add(pair.getKey1());
				groupSet.add(pair.getKey2());
				groupVector.add(groupSet);				
			}
		}
	}
	
	public void printGroup() {
		for (int j = 0; j < groupVector.size(); j++) {
			System.out.println("Group: " + j);
			Set<String> groupSet = groupVector.get(j);
			for (String key: groupSet) {
				System.out.println(key);
			}
			System.out.println();
		}
	}
	
	public void clear() {
		groupVector.clear();
		microVector.clear();
	}
	
	public void println() {
		for (MicroPair pair: microVector) {
			pair.println();
		}
	}
	
	public void sort() {
		Collections.sort(microVector, new MicroComparator());
	}
	
}