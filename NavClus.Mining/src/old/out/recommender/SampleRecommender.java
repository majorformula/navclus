//package old.out.recommender;
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//
//import old.data.pairs.ElementPairManager;
//import old.in.simulator.SampleXMLContextSimulator;
////import old.lib.cluster.macroclusters.MacroClusterPairManager;
////import old.lib.similarity.CosineSimilarityCalculator;
//import renewed.data.elements.ElementManager;
//import salee.in.validator.SampleValidator;
//
//public class SampleRecommender {
//
//	/**
//	 * @param args
//	 */
//	public void run(String testDirectory, MacroClusterPairManager clusterPairManager) {
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
//			for (int i = 0; i <list.length; i++) {
//
//				/*** Creating a Context ***/
//				// node managers...
//				Vector<ElementManager> contextManager;					
//				contextManager = (new SampleXMLContextSimulator()).scanfile(testDirectory, list[i]);
//				//				System.out.println(contextManager.size());	
//
//				/*** Creating a Validator ***/
//				// validator
//				SampleValidator validator = new SampleValidator(testDirectory, list[i]);	
//				//				validator.printChanges();
//
//				/*** Making a recommendation ***/
//				ResultFair result = recommend(list[i], contextManager, clusterPairManager, validator);
//				resultList.add(result);
//			}
//			System.out.println("precision is: " + resultList.calLikelihood());
//			System.out.println("#recommendation is: " + resultList.sum());
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public ResultFair recommend(String file, 
//			Vector<ElementManager> contextManager, 
//			MacroClusterPairManager clusterPairManager, 
//			SampleValidator validator) {
//
//		int iTrue =0, iFalse = 0;
//
//		// for each context
//		for (ElementManager context: contextManager) { // 하나의 파일임...
////			if ((iFalse == 1))
////				break;
////
////			if ((iTrue == 1))
////				break;
//
//			// recommend
//			List<String> recommendationkeys = recommendKey(context, clusterPairManager);
//
//			// recommend 1, 2, 3
//			if (recommendationkeys != null) {
//				ElementPairManager microCluster = new ElementPairManager();
//
//				for (String key: recommendationkeys) {
//					microCluster.merge(clusterPairManager.getMicroClusterManager().get(key));					
//				}								
//
//				// 이게 추천 결과임
//				ElementManager changeManager = microCluster.getChangeManager(); 
//				if (changeManager.size() < 1) { // actually, it should be impossible!
//					continue;
//				}
//
//				/*** Ranking 하는 부분 ***
//				// 추천을 해야 하는지 말아야 하는지....
//
//				// weighting and ranking! : 여기는 좀 있다가 다시 하자...
//
//				// forming a context for a ranking
//				context.copyPackage2HashMap();				
//				ElementManager visitFileManager = microCluster.getVisitFileManager(); // 이게 추천 결과임
//				visitFileManager.copyFile2HashMap();				
//
//				changeManager.weight(context, visitFileManager);
//				changeManager.rank();
////				changeManager.println();
//				 *** Ranking 하는 부분은 삭제 ***/
//
//				// validate
//				boolean bDecision = validator.validate(changeManager);
//
//				// 아직 상위 세 개라는 제약 사항은 두지 않았다...
//				//				System.out.println(bDecision); 
//
////				System.out.println("context:");
////				//				contextManager.sort();
////				context.println();
////
////				//				// change manager
////				System.out.println("recommend:");
////				changeManager.sort();
////				changeManager.println();
//
//				if (bDecision) {
//					iTrue++;
//
//				}
//				else {
//					iFalse++;
//
//					//					System.out.println("Validator's Changes");
//					//					validator.printChanges();		
//					//					
////					System.out.println("context:");
//					//					contextManager.sort();
//					//					context.println();
//					//	
//					//					// visit manager		
//					//	
//					////					visitFileManager.sort();	
//					//					System.out.println("visit:");
//					//					visitFileManager.printHashMap(); 
//					//	
//
//
//				}
//			}				
//		}
//		System.out.println(file + ": #False:" + iFalse + "	#True:" + iTrue + "		" + iTrue / (float)(iTrue + iFalse));
//
//		return new ResultFair(iFalse, iTrue);
//	}
//
//
//	public List<String> recommendKey(ElementManager context, MacroClusterPairManager clusterPairManager) {
//		double max_value = 0;
//		List<String> max_keys = new ArrayList<String>();		
//
//		// cosine similarity calculator
//		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
//
//		for (String keyElement1: clusterPairManager.getMicroClusterManager().keySet()) {
//			ElementPairManager microCluster = clusterPairManager.getMicroClusterManager().get(keyElement1);
//
//			/*** 해당 부분에서 False 추천이 줄어듬 ***/
//			if (!keyElement1.contains(".java"))
//				continue;			
//
//			//			//			if (microCluster.getKeyNum() < 2) // size를 올리면 정확도 높아짐...
//			//			//				continue;
//
//			//			//			if (contextManager.find(keyElement1) == null)
//			//			//				continue;
//
//			if (microCluster.getChangeManager().size() <= 2) // size를 올리면 정확도 높아짐...
//				continue;
//			if (microCluster.getVisitManager().size() <= 3) // size를 올리면 정확도 높아짐...
//				continue;
//
//			double value = calculator.CosineSimilarity(context, microCluster.getVisitManager());
//			if (value > 0.3) {
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
//		if (max_value > 0.3) {
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
