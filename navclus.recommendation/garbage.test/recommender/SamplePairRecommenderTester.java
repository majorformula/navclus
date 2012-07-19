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
//import old.in.reader.SampleXMLPairSegmenter;
//import old.lib.cluster.macroclusters.MacroClusterManager;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.out.recommender.SampleRecommender;
//import renewed.in.reader.SampleXMLSegmenter;
//import renewed.lib.cluster.microclusters.MicroClusterManager;
//
//
//
//
//public class SamplePairRecommenderTester {
//	
//	static String trainDirectory = "H:/Data/sample-Data";	
//	static String testDirectory = "H:/Data/sample-Test";
//	static int iThreshold = 7 ;
//	
//	public static void main(String[] args) {
//		
//		/*** Mining ***/		
//		// Micro-clustering
//		MicroClusterPairManager microClusterPairManager = (new SampleXMLPairSegmenter()).scanfile(trainDirectory);
////		microClusterPairManager.printPair();
//		
//		// Print Micro-clusters	
//		microClusterPairManager.ignore(iThreshold);
////		microClusterPairManager.printKeys();
////		System.out.println("list size:" + microClusterPairManager.size());	
//		
//		// macro-clustering ... 1Â÷
//		MacroClusterPairManager macroClusterPairManager = new MacroClusterPairManager(microClusterPairManager);
//		macroClusterPairManager.cluster(0.2);
////				
//		// Print Macro-clusters
////		macroClusterManager.println(3);	// with sorting
//		
//		/*** Recommending ***/
//		SampleRecommender recommender = new SampleRecommender();
//		recommender.run(testDirectory, macroClusterPairManager);
//				
//	}
//
//}
