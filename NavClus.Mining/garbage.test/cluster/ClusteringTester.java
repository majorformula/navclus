package old.test.cluster;

import old.data.matrix.IList;
import old.lib.cluster.macroclusters.Clusters;
//import old.lib.cluster.macroclusters.MacroClusterManager;
import renewed.data.elements.ElementManager;
import renewed.data.matrix.MatrixManager;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.similaritymatrix.CosineSimilarityCalculator;

public class ClusteringTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// micro-clustering
		MicroClusterManager microClusterManager = new MicroClusterManager();		
		microClusterManager.insert2segment("a");
		microClusterManager.insert2segment("b");
		microClusterManager.insert2segment("c");
		microClusterManager.insert2segment("a");
		microClusterManager.insert2segment("b");
		microClusterManager.insert2segment("d");
		microClusterManager.insert2segment("b");
		microClusterManager.insert2segment("d");
		microClusterManager.insert2segment("done");
		microClusterManager.insert2segment("e");
		microClusterManager.insert2segment("f");
		microClusterManager.insert2segment("g");
		microClusterManager.insert2segment("e");
		microClusterManager.insert2segment("f");
		microClusterManager.insert2segment("c");
		microClusterManager.insert2segment("f");
		microClusterManager.insert2segment("c");
		microClusterManager.insert2segment("f");
		microClusterManager.insert2segment("done");
		microClusterManager.insert2segment("c");
		microClusterManager.insert2segment("a");
		microClusterManager.insert2segment("b");
		microClusterManager.insert2segment("x");
		microClusterManager.insert2segment("b");
		microClusterManager.insert2segment("d");
		microClusterManager.insert2segment("done");
		
		// Print Micro-clusters
		System.out.println("list size:" + microClusterManager.size());		
		microClusterManager.println();		

		// macro-clustering
//		MacroClusterManager macroClusterManager = new MacroClusterManager(microClusterManager);
//		macroClusterManager.cluster(0.5);
//		macroClusterManager.println(0);	 
	}	
}
