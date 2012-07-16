//package old.test.recommender;
//
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.Vector;
//
//import old.data.pairs.ElementPairManager;
//import old.in.reader.RealXMLPairSegmenter;
//import old.in.simulator.RealXMLContextSimulator;
//import old.in.validator.Validator;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.out.recommender.RealRecommender;
//import old.out.recommender.ResultFair;
//import old.out.recommender.ResultList;
//import old.out.recommender.TFIDFRecommender;
//import renewed.data.elements.ElementManager;
//
//
//
//
//public class TFIDFRecommenderTester {
//	
//	static int iThreshold = 0 ; // 샘플에서는 7이었는데 이를 실제 데이타에서는 3으로 낮추었다.
//
//	static String trainDirectory = "H:/Data/Platform-Data";	
//	static String testDirectory = "H:/Data/Platform-Test";
//	
////	static String trainDirectory = "E:/Data/C1-3Data";
////	static String testDirectory = "E:/Data/C1-3Test";
//	
////	static String trainDirectory = "E:/Data/Mylyn-Data";	
////	static String testDirectory = "E:/Data/Mylyn-Test";
//
//	/**
//	 * @TF for Document
//	 * @IDF for Term
//	 */
//	public static void main(String[] args) {
//
//		try {
//			/*** Mining ***/		
//			// Micro-clustering
//			MicroClusterPairManager microClusterPairManager = (new RealXMLPairSegmenter()).scanfile(trainDirectory);
//			microClusterPairManager.printPair();
//			System.out.println(microClusterPairManager.size());
//			
//			// Print Micro-clusters	
//			microClusterPairManager.ignore(iThreshold);
////			microClusterPairManager.printKeys();
////			System.out.println("list size:" + microClusterPairManager.size());	
//			
//			// macro-clustering ... 1차
//			MacroClusterPairManager macroClusterManager = new MacroClusterPairManager(microClusterPairManager);
////			macroClusterManager.cluster(0.0);
////					
//			// Print Macro-clusters
////			macroClusterManager.println(0);	// with sorting
//			
//			
//			/*** TF/IDF ***/				
//
//			// Calculate TF for Terms...
//			MicroClusterPairManager clusterPairManagers = macroClusterManager.getMicroClusterManager();
//			
//			Set<String> keyStrings = clusterPairManagers.keySet();
//			for (String keyString: keyStrings) {
//				ElementPairManager microPairManager = clusterPairManagers.get(keyString);
//				ElementManager docManager = microPairManager.getVisitManager();
//				docManager.tf(); // print
//			}
//
//
//			// Calculate IDF for Terms...
//			ElementManager termManager = new ElementManager();						
//			//			Set<String> keyStrings = clusterPairManagers.keySet();
//			for (String keyString: keyStrings) {
//				ElementPairManager microPairManager = clusterPairManagers.get(keyString);
//				ElementManager docManager = microPairManager.getVisitManager();
//				termManager.mergewithDoc(docManager);
//			}
//			termManager.idf(clusterPairManagers.size());
//			//			termManager.sort();
//			//			termManager.println();		
//
//			// Calculate TF/IDF for Terms of each Document...
//			for (String keyString: keyStrings) {
//				ElementPairManager microPairManager = clusterPairManagers.get(keyString);
//				ElementManager docManager =  microPairManager.getVisitManager();
//				docManager.tfidf(termManager, clusterPairManagers.size());
//			}
//
//			// Test Files...
//			File file = new File(testDirectory);		
//			String[] list = file.list(new FilenameFilter() 
//			{ 
//				@Override 
//				public boolean accept(File dir, String name)  
//				{ 
//					return name.endsWith(".xml"); 
//				} 
//			});	
//
//			/*** Recommending Data ***/
//			ResultList resultList = new ResultList();
//			for (int i = 0; i <list.length; i++) {
//
//				/*** Creating a Context ***/
//				// node managers...
//				Vector<ElementManager> contextManagers;					
//				contextManagers = (new RealXMLContextSimulator()).scanfile(testDirectory, list[i]);
//				//			System.out.println(nodeManagers.size());	
//
//				/*** Creating a Validator ***/
//				// validator
//				Validator validator = new Validator(testDirectory, list[i]);	
////				System.out.println("Validator's Changes");
////				validator.printChanges();
//
//				/*** Making a recommendation ***/
//				TFIDFRecommender recommender = new TFIDFRecommender();
//				ResultFair result = recommender.recommend(list[i], contextManagers, clusterPairManagers, validator, termManager);
//				resultList.add(result);
//			}
//			System.out.println("precision is: " + resultList.calLikelihood());
//			System.out.println("#recommendation is: " + resultList.sum());											
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//
//}
