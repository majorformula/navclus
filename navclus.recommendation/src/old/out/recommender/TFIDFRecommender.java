//package old.out.recommender;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//import old.data.pairs.ElementPairManager;
//import old.in.validator.Validator;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import renewed.data.elements.Element;
//import renewed.data.elements.ElementManager;
//
//
//
//public class TFIDFRecommender {
//
//public static ResultFair recommend(String file, 
//		Vector<ElementManager> contextManagers, 
//		MicroClusterPairManager clusterPairManager, 
//		Validator validator,
//		ElementManager termManager) {
//
//	int iTrue =0, iFalse = 0;
//	for (ElementManager contextManager: contextManagers) { // 하나의 파일임...
//
//		contextManager.tf();
//		contextManager.tfidf(termManager, 2);			
//
//		//			if (iFalse == 1)
//		//				break;
//
//		// recommend
//		List<String> recommendationkeys = recommendKey(contextManager, clusterPairManager);
//
//
//		// recommend 1, 2, 3
//		if (recommendationkeys != null) {
//			ElementPairManager microCluster = new ElementPairManager();
//
//			for (String key: recommendationkeys) {
//				microCluster.merge(clusterPairManager.get(key));					
//			}								
//
//			ElementManager changeManager = microCluster.getChangeManager(); // 이게 추천 결과임
//			if (changeManager.size() < 1) { // actually, it should be impossible!
//				continue;
//			}
//
//			
////			 weighting and ranking!...forming a context for a ranking
//			contextManager.copyPackage2HashMap();
////			
//			ElementManager visitFileManager = microCluster.getVisitFileManager(); // 이게 추천 결과임
//			visitFileManager.copyFile2HashMap();				
//
//			changeManager.weight(contextManager, visitFileManager);
//			changeManager.rank();
//			
//			// validate
//			boolean bDecision = validate(changeManager, validator);
//
//			// 아직 상위 세 개라는 제약 사항은 두지 않았다...
//			//				System.out.println(bDecision); 
//			if (bDecision) {
//				iTrue++;
//				//										System.out.println("change:");						
//				//										resultManager.println();
//			}
//			else {
//				iFalse++;
//
////				System.out.println("context:");
////				contextManager.println();
////
////				// visit manager
////				NodeManager visitManager = microCluster.getVisitManager(); // 이게 추천 결과임
////				visitManager.sort();				
////
////				// print
////				System.out.println("visit:");
////				visitManager.println();
////
////				// change manager
////				System.out.println("change:");						
////				resultManager.println();
//			}
//		}				
//	}
//	System.out.println(file + ": #False:" + iFalse + "	#True:" + iTrue + "		" + iTrue / (float)(iTrue + iFalse));
//
//	return new ResultFair(iFalse, iTrue);
//}
//
//public static List<String> recommendKey(ElementManager contextManager, MicroClusterPairManager clusterPairManager) {
//	double max_value = 0;
//	List<String> max_keys = new ArrayList<String>();		
//
//	// cosine similarity calculator
//	//		CosineSimilarityCalculatorTop calculator = new CosineSimilarityCalculatorTop();
//
//
//	//		System.out.println("recommend:" + i++);
//	//		for (String keyString: keyStrings) {
//	//			NodePairManager microPairManager = clusterPairManagers.get(keyString);
//	//			NodeManager docManager =  microPairManager.getVisitManager();
//	//			
//	//			CosineSimilarity(contextManager,docManager);		
//	//		}
//
//	for (String keyElement1: clusterPairManager.keySet()) {
//		ElementPairManager microCluster = clusterPairManager.get(keyElement1);
//
//		/*** 해당 부분에서 False 추천이 줄어듬 ***/
//		if (!keyElement1.contains(".java"))
//			continue;			
//
//		//			if (microCluster.getKeyNum() < 2) // size를 올리면 정확도 높아짐...
//		//				continue;
//
//		//			if (contextManager.find(keyElement1) == null)
//		//				continue;
//
//		if (microCluster.getChangeManager().size() <= 2) // size를 올리면 정확도 높아짐...
//			continue;
//		if (microCluster.getVisitManager().size() <= 3) // size를 올리면 정확도 높아짐...
//			continue;
//
//		//			System.out.println("cluster:");
//		//			microCluster.getVisitManager().println();
//
//		double value = TFIDFSimilarity(contextManager, microCluster.getVisitManager());  // IF/IDF
//		//			System.out.println(value);
//		if (value > 0.70) {
//
//			// ignore if it is a creator
//			if (keyElement1.indexOf(".java") == -1)
//				continue;
//			if (keyElement1.indexOf(",") == -1)
//				continue;
//
//			if (keyElement1.indexOf(",") + 2 >= keyElement1.indexOf(".java"))
//				continue;
//
//			String filename = keyElement1.substring(keyElement1.indexOf(",") + 2, keyElement1.indexOf(".java"));
//			if ((keyElement1.lastIndexOf(filename)+ filename.length()) == (keyElement1.length()))
//				continue;
//			//					System.out.println("it is a creator function");
//
//			//			if (value > max_value ) { // && (keyElement1.contains(".java"))){ // remove the package level
//			//			if ((value > max_value ) && (keyElement1.contains(".java"))){ // remove the package level
//			if (value > max_value)
//				max_value = value;
//			max_keys.add(keyElement1);
//			//						System.out.print("num: " + ++ num + "  ");
//
//		}
//	}
//
//	/*** 해당 부분에서 False 추천이 줄어듬 ***/
//	if (max_value > 0.70) {
////		System.out.println(max_value + ":"  + max_keys );
//		//			contextManager.println();
//
//		return max_keys;
//	}
//	else {
//		return null;
//	}
//}
//
//public static boolean validate(ElementManager resultManager, Validator validator) { // 이건 아마 삭제해야 할 듯...
//	boolean bDecision = false;
//
////	resultManager.sort();
//	for (int i = 0; i < 3 /*resultManager.size()*/; i++) {
//		Element node =  resultManager.elementAt(i);
//
//		if (validator.contain(resultManager.elementAt(i).getName())) {
//			bDecision = true;
//			//				node.println(); // the correct answer
//		}			
//	}		
//	return bDecision;
//}
//	
//public static double TFIDFSimilarity(ElementManager vectorA, ElementManager vectorB) {
//	double value = 0;		
//	double XY = 0;
//	double SumX = 0;
//	double SumY = 0;
//
//	ElementManager termManager = new ElementManager();
//	termManager.mergewithXDoc(vectorA);
//	termManager.mergewithYDoc(vectorB);
//
//	for (int i = 0; i < termManager.size(); i++) {
//		Element nodeXY = termManager.elementAt(i);
//
//		SumX = SumX +  nodeXY.getXTFIDF() * nodeXY.getXTFIDF();
//		SumY = SumY +  nodeXY.getYTFIDF() * nodeXY.getYTFIDF();
//
//		XY = XY + nodeXY.getXTFIDF() * nodeXY.getYTFIDF();
//
//		//			System.out.println(nodeXY.getName() + " " + nodeXY.getCount() + " = " + nodeXY.getX() + " + " + nodeXY.getY() + 
//		//			" : " + nodeXY.getXTFIDF() + ", " + nodeXY.getYTFIDF());
//	}
//
//	value = XY / Math.sqrt(SumX * SumY);
//
//	//		System.out.println(/*AB + " " + SumA + " " + SumB + " " + */ value);
//
//	return value;
//}
//
//
//}
