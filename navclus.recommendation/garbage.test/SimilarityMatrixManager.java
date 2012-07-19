package renewed.lib.cluster.similaritymatrix;

//import old.data.matrix.IList;
//import old.lib.cluster.macroclusters.Clusters;
import java.util.List;

import renewed.data.elements.ElementManager;
import renewed.data.matrix.MatrixManager;
import renewed.lib.cluster.microclusters.MicroClusterManager;

public class SimilarityMatrixManager {
	MicroClusterManager microClusterManager;// Input
	CosineSimilarityCalculator calculator = new CosineSimilarityCalculator(); // Process	
	MatrixManager matrixManager;  // Output

	public SimilarityMatrixManager(MicroClusterManager microClusterManager) {
		this.microClusterManager = microClusterManager;
		matrixManager = new MatrixManager(microClusterManager.size());
	}
	
	public MicroClusterManager getMicroClusterManager() {
		return microClusterManager;
	}
	
	public MatrixManager getMatrixManager() {
		return matrixManager;
	}

	public void setupSimilarityMatrix(double mThreshold) {
		
		List<String> indexArray = microClusterManager.getIndexArray();
		for (String keyElement1: indexArray) {
			ElementManager nodeManager1 = microClusterManager.get(keyElement1);
			
			// no compute in this case 
			if (nodeManager1.getUpdated() == 0) continue;
			
			for (String keyElement2: indexArray) {
				ElementManager nodeManager2 = microClusterManager.get(keyElement2);
				
				// no compute in this case 
				if (indexArray.indexOf(keyElement1) ==  indexArray.indexOf(keyElement2)) continue;
				
				// compute the similarity
				double value = calculator.CosineSimilarity(nodeManager1, nodeManager2);
				
				if (value > mThreshold)
					matrixManager.setMatrix(indexArray.indexOf(keyElement1), indexArray.indexOf(keyElement2), value);				
			}
		}
	}
	
	public void print() {
		matrixManager.print();
}
}
