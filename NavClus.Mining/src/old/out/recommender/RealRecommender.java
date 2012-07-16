//package old.out.recommender;
//
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//import old.data.pairs.ElementPairManager;
//import old.in.simulator.RealXMLContextSimulator;
//import old.in.validator.Validator;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.lib.similarity.CosineSimilarityCalculator;
//import renewed.data.elements.Element;
//import renewed.data.elements.ElementManager;
//
//
//
//public class RealRecommender {
//
//	double similarityThreshold ;
//	
//	public RealRecommender(double similarityThreshold) {
//		this.similarityThreshold = similarityThreshold;
//	}
//
//	/**
//	 * run all test files ...
//	 */
//	public void run(String testDirectory, MicroClusterPairManager clusterPairManager) {
//
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
//			/*** Recommending Data ***/
//			ResultList resultList = new ResultList();
//			
//			// loop for each file ...
//			int numQueries = 0;
//			for (int i = 0; i <list.length; i++) { 
//
//				/*** Creating a Context per each file ***/
//				Vector<ElementManager> contextManagers;					
//				contextManagers = (new RealXMLContextSimulator()).scanfile(testDirectory, list[i]);
//				numQueries = numQueries + contextManagers.size();
//
//				/*** Creating a Validator per each file ***/
//				Validator validator = new Validator(testDirectory, list[i], clusterPairManager.getSet());					
//
//				/*** Making a recommendation per each file ***/
//				ResultFair result = checkRecommend(list[i], contextManagers, clusterPairManager, validator);
//				resultList.add(result);
//			}
//			
//			System.out.println("Likelihood is: " + resultList.calLikelihood());
//			System.out.println("Precision is: " + resultList.calPrecision());
//			System.out.println("Recall is: " + resultList.calRecall());
//			System.out.println("Feedback is: " + (double) resultList.sum() / (double) numQueries);
//			System.out.println("#recommendations is: " + resultList.sum());
//			System.out.println("#queries is: " + numQueries);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * run each test files ...
//	 */
//	public ResultFair checkRecommend(String file, 
//			Vector<ElementManager> contextManager, 
//			MicroClusterPairManager clusterPairManager, 
//			Validator validator) {
//
//		int iTrue =0, iFalse = 0;
//		double sumOfPrecision = 0, sumOfRecall = 0;
//
//		// for each context
//		for (ElementManager context: contextManager) { // 하나의 파일임...
//			
//			// recommend per each context
//			ElementManager changeManager = recommend(context, clusterPairManager);
//
//			// validate
//			if (changeManager == null)
//				continue;
//			if (changeManager.size() == 0)
//				continue;			
//						
//			boolean bDecision = validator.validate(changeManager);
//			sumOfPrecision = sumOfPrecision + validator.calPrecision(changeManager);
//			sumOfRecall = sumOfRecall + validator.calRecall(changeManager);
//			if (bDecision) {
//				iTrue++;
//
//			}
//			else {
//				iFalse++;
//
//				//					System.out.println("Validator's Changes");
//				//					validator.printChanges();		
//				//					
//				//					System.out.println("context:");
//				//					contextManager.sort();
//				//					contextManager.printHashMap();
//				//	
//				//					// visit manager		
//				//					visitFileManager.sort();	
//				//					System.out.println("visit:");
//				//					visitFileManager.printHashMap(); 
//				//	
//				//					// change manager
//				//					System.out.println("change:");						
//				//					changeManager.println();
//			}
//		}
//		System.out.println(file + ": #False:" + iFalse + "	#True:" + iTrue + "		" + iTrue / (float)(iTrue + iFalse)+ "     precision: " + sumOfPrecision / ((double)(iTrue + iFalse)) + "		recall:" + sumOfRecall / ((double)(iTrue + iFalse)));
//		return new ResultFair(iFalse, iTrue, sumOfPrecision, sumOfRecall);
//	}
//
//	public ElementManager recommend(ElementManager context, MicroClusterPairManager clusterPairManager) {
//
//		List<String> recommendationkeys = recommendKey(context, clusterPairManager);
//
//		// recommend 1, 2, 3
//		if (recommendationkeys == null) {
//			return null;
//		}
//
//		ElementPairManager microCluster = new ElementPairManager();
//
//		for (String key: recommendationkeys) {
//			microCluster.merge(clusterPairManager.get(key));					
//		}								
//
//		// 이게 추천 결과임
//		int minSupport = 0;
//		ElementManager changeManager = microCluster.getChangeManager(minSupport); 
//		if (changeManager.size() < 1) { 
//			return null;
//		}
//
//		// weighting and ranking!
//
//		// forming a context for a ranking
//		context.copyPackage2HashMap();
//
//		ElementManager visitFileManager = microCluster.getVisitFileManager(); // 이게 추천 결과임
//		visitFileManager.copyFile2HashMap();				
//
//		changeManager.weight(context, visitFileManager);
//		changeManager.rank();
//		//			changeManager.println();
//		return changeManager;
//	}
//
//	public List<String> recommendKey(ElementManager contextManager, MicroClusterPairManager clusterPairManager) {
//		double max_value = 0;
//		List<String> max_keys = new ArrayList<String>();
////		ElementManager keyManager = new ElementManager();
//
//		// cosine similarity calculator
//		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
//
//		for (String keyElement1: clusterPairManager.keySet()) {
//			ElementPairManager microCluster = clusterPairManager.get(keyElement1);
//
//			/*** 해당 부분에서 False 추천이 줄어듬 ***/
//			if (!keyElement1.contains(".java"))
//				continue;			
//
//			//			if (microCluster.getKeyNum() < 2) // size를 올리면 정확도 높아짐...
//			//				continue;
//
//			//			if (contextManager.find(keyElement1) == null)
//			//				continue;
//
//			if (microCluster.getChangeManager().size() <= 2) // size를 올리면 정확도 높아짐...
//				continue;
//			if (microCluster.getVisitManager().size() <= 3) // size를 올리면 정확도 높아짐...
//				continue;
//
//			double value = calculator.CosineSimilarity(contextManager, microCluster.getVisitManager());
//			if (value > similarityThreshold) {
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
//				if (value > max_value)
//					max_value = value;
//
////				keyManager.add(new Element(keyElement1, value));
//				max_keys.add(keyElement1);
//				//						System.out.print("num: " + ++ num + "  ");
//
//			}
//		}
//		
////		keyManager.sortSimilarity();
////		int length = 5;
////		if (keyManager.size() < 5)
////			length = keyManager.size();
////		
////		for (int i = 0; i < length; i++) {
////			String key = keyManager.elementAt(i).getName();			
////			max_keys.add(key);
////		}		
//
//		/*** 해당 부분에서 False 추천이 줄어듬 ***/
//		if (max_value > similarityThreshold) {
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
//}
