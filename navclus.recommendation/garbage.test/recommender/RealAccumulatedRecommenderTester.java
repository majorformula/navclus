//package old.test.recommender;
//
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//
//import old.in.reader.RealXMLPairSegmenter;
//import old.in.reader.SampleXMLPairSegmenter;
//import old.lib.cluster.macroclusters.MacroClusterManager;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.out.recommender.RealAccumulatedRecommender;
//import old.out.recommender.RealRecommender;
//import old.out.recommender.SampleRecommender;
//import renewed.in.reader.SampleXMLSegmenter;
//import renewed.lib.cluster.microclusters.MicroClusterManager;
//
//
//
//
//public class RealAccumulatedRecommenderTester {
//
////	static String trainDirectory = "H:/Data/C1-3Data"; 	
////	static String testDirectory = "H:/Data/C1-3Test"; // Similarity = 0.075
//	
//	static String trainDirectory = "H:/Data/Platform-Data";	
//	static String testDirectory = "H:/Data/Platform-Test"; // Similarity = 0.02
//	static int iThreshold = 0 ; // 샘플에서는 7이었는데 이를 실제 데이타에서는 3으로 낮추었다.
//	
//	public static void main(String[] args) {
//		
//		/*** Mining ***/		
//		// Micro-clustering
//		MicroClusterPairManager microClusterPairManager = (new RealXMLPairSegmenter()).scanfile(trainDirectory);
////		microClusterPairManager.printPair();
//		
//		// Print Micro-clusters	
//		microClusterPairManager.ignore(iThreshold);
////		microClusterPairManager.printKeys();
////		System.out.println("list size:" + microClusterPairManager.size());	
//		
//		// macro-clustering ... 1차
//		MacroClusterPairManager macroClusterManager = new MacroClusterPairManager(microClusterPairManager);
//		macroClusterManager.cluster(0.5);
////				
//		// Print Macro-clusters
////		macroClusterManager.println(0);	// with sorting
//		
//		/*** Recommending ***/
//		RealAccumulatedRecommender recommender = new RealAccumulatedRecommender(0.30); // Validator와 Context Manager가 일관성있게 바뀌어야 한다.
//		recommender.run(testDirectory, macroClusterManager);				
//	}
//
//}
