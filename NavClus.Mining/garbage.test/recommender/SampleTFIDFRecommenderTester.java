//package old.test.recommender;
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//import old.data.elements.ElementManager;
//import old.data.pairs.ElementPairManager;
//import old.in.reader.SampleXMLSegmenter;
//import old.in.simulator.SampleXMLContextSimulator;
//import old.lib.cluster.macroclusters.MacroClusterManager;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterManager;
//import old.lib.similarity.CosineSimilarityCalculator;
//import old.salee.cs.similarity.VectorManager;
//
//
//
//
//
//public class SampleTFIDFRecommenderTester {
//
//	static String trainDirectory = "H:/Data/Sample-Data";	
//	static String testDirectory = "H:/Data/sample-TestT4";
//	static int iThreshold = 7 ;
//
//	static VectorManager vectorManager; // = new VectorManager();
//
//	public static void main(String argv[])  {
//
//		// Micro-clustering
//		MicroClusterManager microClusterManager = (new SampleXMLSegmenter()).scanfile(trainDirectory);
//
//		//		// Print Micro-clusters	
//		microClusterManager.ignore(iThreshold);
//		System.out.println("list size:" + microClusterManager.size());	
//
//		// macro-clustering ... 1차
//		MacroClusterManager macroClusterManager = new MacroClusterManager(microClusterManager);
//		macroClusterManager.cluster(0.5);
//		
//		System.out.println("list size:" + macroClusterManager.getMicroClusterManager().size());	
//
//		//		// Print Macro-clusters
//		//		macroClusterManager.println(1);	// with sorting
//
//		/************** TF/IDF: Start **************************************/
//		vectorManager = new VectorManager();
//		vectorManager.createVector(macroClusterManager.getMicroClusterManager().getHashmap());
//
//		/************** TF/IDF: End **************************************/
//		try {
//
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
//			for (int i = 0; i <list.length; i++) {
//
//				/*** Creating a Context ***/
//				// node managers...
//				Vector<ElementManager> contextManager;					
//				contextManager = (new SampleXMLContextSimulator()).scanfile(testDirectory, list[i]);
//
//				System.out.println("Context: ");
//
//				contextManager.elementAt(0).println();
//				
//				// get a recommend key
////				String queryTerms = getQueryTerms(contextManager.elementAt(0));
//				vectorManager.compareSimilarity(contextManager.elementAt(0));
//				
////				List<String> recommendationkeys = recommendKey(contextManager.elementAt(0), macroClusterManager);
////				System.out.println(recommendationkeys);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	public static List<String> recommendKey(ElementManager context, MacroClusterManager clusterManager) {
//		double max_value = 0;
//		List<String> max_keys = new ArrayList<String>();		
//
//		// cosine similarity calculator
//		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
//
//		for (String keyElement1: clusterManager.getMicroClusterManager().keySet()) {
//			ElementManager microCluster = clusterManager.getMicroClusterManager().get(keyElement1);
//
//			/*** 해당 부분에서 False 추천이 줄어듬 ***/
//			if (!keyElement1.contains(".java"))
//				continue;
//
//			//		if (keyElement1.endsWith(".java"))
//			//			continue;
//
//			//			//			if (microCluster.getKeyNum() < 2) // size를 올리면 정확도 높아짐...
//			//			//				continue;
//
//			//			//			if (contextManager.find(keyElement1) == null)
//			//			//				continue;
//
//			if (microCluster.size() <= 3) // size를 올리면 정확도 높아짐...
//				continue;
//
//			double value = calculator.CosineSimilarity(context, microCluster);
//			if (value > 0.0) {
//				//				System.out.println(value);
//
//				// ignore if it is a creator
//				if (keyElement1.indexOf(".java") == -1)
//					continue;
//				if (keyElement1.indexOf(",") == -1)
//					continue;
//
//				if (keyElement1.indexOf(",") + 2 >= keyElement1.indexOf(".java"))
//					continue;
//
//				String filename = keyElement1.substring(keyElement1.indexOf(",") + 2, keyElement1.indexOf(".java"));
//				if ((keyElement1.lastIndexOf(filename)+ filename.length()) == (keyElement1.length()))
//					continue;
//				//					System.out.println("it is a creator function");
//
//				//			if (value > max_value ) { // && (keyElement1.contains(".java"))){ // remove the package level
//				//			if ((value > max_value ) && (keyElement1.contains(".java"))){ // remove the package level
//				if (value > max_value) {
//					max_value = value;
//					max_keys.clear();
//					max_keys.add(keyElement1);
//					//						System.out.print("num: " + ++ num + "  ");
//				}
//
//			}
//		}
//		//
//		/*** 해당 부분에서 False 추천이 줄어듬 ***/
//		if (max_value > 0.0) {
//			//			System.out.println(max_value + ":"  + max_keys );
//			//			contextManager.println();
//
//			return max_keys;
//		}
//		else {
//			return null;
//		}
//	}
//
//	private static String getQueryTerms(ElementManager context) {
//		String queryTerms;
//
//		queryTerms = "";
//		for (String element_name : context.getElementSet()) {
//			queryTerms = queryTerms + element_name + "; ";
//		}
//		return queryTerms;
//	}
//
//
//}
