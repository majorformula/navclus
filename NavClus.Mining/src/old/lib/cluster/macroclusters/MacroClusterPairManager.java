//// ���� ������ �� �� ���� �ƴϴ�. ��ũ�ο� ����Ʈ�δ� ���� ����Ÿ�� �����ϰ� ������, ���� �ٸ� ������ �ϴ°� ������ �Ѵ�.
//
//package old.lib.cluster.macroclusters;
//
//import old.data.matrix.IList;
//import old.data.pairs.ElementPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.lib.similarity.CosineSimilarityCalculator;
//import renewed.data.elements.ElementManager;
//import renewed.data.matrix.sparse.MatrixManager;
//
//public class MacroClusterPairManager {
//
//	// Calculating cosine similarity
//	CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
//	MatrixManager matrixManager;
//	MicroClusterPairManager microClusterPairManager;
//
//	public MacroClusterPairManager(MicroClusterPairManager microClusterPairManager) {
//		this.matrixManager = new MatrixManager(microClusterPairManager.size());
//		this.microClusterPairManager = microClusterPairManager;
//	}
//	
//	public MicroClusterPairManager getMicroClusterManager() {
//		return microClusterPairManager;
//	}
//
//	public void cluster(double mThreshold) {
//
//		String[] keys = new String[microClusterPairManager.size()];
//
//		int i = 0;
//		for (String keyElement1: microClusterPairManager.keySet()) {
//			keys[i] = keyElement1;
//			ElementPairManager nodeManager1 = microClusterPairManager.get(keyElement1);
//			//			nodeManager1.sort(); // ���Ⱑ ������...�̰� ������ Ű�� �ٲ����
//
//			int j = 0;
//			for (String keyElement2: microClusterPairManager.keySet()) {
//				ElementPairManager nodeManager2 = microClusterPairManager.get(keyElement2);
//				//				nodeManager2.sort();
//
//				//				System.out.println(i + " " + j);
//				double value = calculator.CosineSimilarity(nodeManager1.getVisitManager(), nodeManager2.getVisitManager());
//				matrixManager.setMatrix(i, j, value);
//				j++;
//			}
//			i++;
//		}
//		//		matrixManager.print();
//
//		// get clusters & we have keys
//		Clusters clusters = new Clusters(mThreshold);
//		clusters.cluster(matrixManager.getMatrix());
////		clusters.print();
//
//		// merge and remove
////		System.out.println("Size:" + clusters.size());
//
//		for (IList iList: clusters.getVector()) { 
//			String key2Insert = keys[iList.get(0)]; // sorting���ؼ� �߸��� ����...
////			System.out.println("insert: " + key2Insert);		
//
//			ElementPairManager nodeManager2Insert = microClusterPairManager.get(key2Insert);
////			nodeManager2Insert.println();
//
//			for (int x = 1; x < iList.size(); x++) {
//				String key2Remove = keys[iList.get(x)];
//				if (key2Remove.endsWith(".java"))
//					continue;
////				System.out.println("remove: " + key2Remove);
//
//				//				System.out.println(iList.get(0) + " ***" + iList.get(l) + " *** " + key2Remove);
//				ElementPairManager nodeManager2Remove = microClusterPairManager.get(key2Remove); // it gets the null...because the key has been already deleted...
////				nodeManager2Remove.println();
//
//				// merge
//				nodeManager2Insert.merge(nodeManager2Remove);
////				System.out.println("merge: " );				
////				nodeManager2Insert.println();
//				microClusterPairManager.put(key2Insert, nodeManager2Insert);
//
//				// remove
//				microClusterPairManager.remove(key2Remove);
//			}
//		}
//
//	}
//
//	public void println(int iThreshold) {	
//
//		int num = 0;
//		for (String keyElement1: microClusterPairManager.keySet()) {
//			//			System.out.println("num: " + ++ num);
//			ElementPairManager elementPairManager = microClusterPairManager.get(keyElement1);
//						elementPairManager.sort();
//
//			//			for (String keyElement2: microClusterManager.keySet()) {
//			//				ElementManager nodeManager2 = microClusterManager.get(keyElement2);
//			//				nodeManager2.sort();
//			//				
//			//				System.out.println(calculator.CosineSimilarity(nodeManager1, nodeManager2));				
//			//			}
//		}
//
//		microClusterPairManager.println(iThreshold); // cut the bottom part...
//		System.out.println("list size:" + microClusterPairManager.size());
//	}
//
//}
