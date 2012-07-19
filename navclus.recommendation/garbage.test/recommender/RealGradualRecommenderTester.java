//package old.test.recommender;
//
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FilenameFilter;
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//
//import old.in.reader.RealXMLPairSegmenter;
//import old.in.reader.SampleXMLPairSegmenter;
//import old.in.simulator.RealXMLContextSimulator;
//import old.in.validator.Validator;
//import old.lib.cluster.macroclusters.MacroClusterManager;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.out.recommender.RealRecommender;
//import old.out.recommender.ResultFair;
//import old.out.recommender.ResultList;
//import old.out.recommender.SampleRecommender;
//import renewed.data.elements.ElementManager;
//import renewed.in.reader.SampleXMLSegmenter;
//import renewed.lib.cluster.microclusters.MicroClusterManager;
//
//
//
//
//
//public class RealGradualRecommenderTester {
//
////	static String trainDirectory = "H:/Data/C1-3Data"; 	
////	static String testDirectory = "H:/Data/C1-3Test"; // Similarity = 0.075
//	
//	static String directory = "E:/Project_Mylyn";	
//	static int iThreshold = 0 ; // 샘플에서는 7이었는데 이를 실제 데이타에서는 3으로 낮추었다.
//	
////	public static  FileOutputStream Output;
////	public static  PrintStream outfile;
//	
//	public static void main(String[] args) {
//		
////		//must use a try/catch statement for it to work
////		try
////		{     
////			Output = new FileOutputStream("out.txt");
////			outfile = new PrintStream(Output);
////		}        
////		catch(Exception e)
////		{
////			System.out.println("Could not load file!");
////		}
//		
//		// Micro-clustering
//		MicroClusterPairManager microClusterPairManager = new MicroClusterPairManager() ; // = (new RealXMLPairSegmenter()).scanfile(trainDirectory);
//		RealXMLPairSegmenter realXMLPairSegmenter = new RealXMLPairSegmenter();
//		
//		try {
//			
//			/*** Reading File List ***/ // train data
//			File file = new File(directory); 
//			String[] list = file.list(new FilenameFilter() 
//			{ 
//				@Override 
//				public boolean accept(File dir, String name)  
//				{ 
//					return name.endsWith(".xml"); 
//				} 
//			});
//			
//			RealRecommender recommender = new RealRecommender(0.4); // 항상 수정해야 하는 곳
//			ResultList resultList = new ResultList();		
//			// loop for each file ...
//			int numQueries = 0;
//			for (int i = 0; i <list.length; i++) {
//			
//			/*** Recommending ***/ // Validator와 Context Manager가 일관성있게 바뀌어야 한다.
//
//				/*** Creating a Context per each file ***/
//				Vector<ElementManager> contextManagers;					
//				contextManagers = (new RealXMLContextSimulator()).scanfile(directory, list[i]);
//				numQueries = numQueries + contextManagers.size();
//
//				/*** Creating a Validator per each file ***/
//				Validator validator = new Validator(directory, list[i], microClusterPairManager.getSet());					
//
//				/*** Making a recommendation per each file ***/
//				ResultFair result = recommender.checkRecommend(list[i], contextManagers, microClusterPairManager, validator);				
//				resultList.add(result);
//				
////				//print line by line
////				outfile.print(list[i] +": ");
////				// new part:
////				outfile.print("Likelihood_1 is, " + resultList.calLikelihood() + " : ");
////				outfile.print("Likelihood_2 is, " + resultList.calculate1() + " : ");
////				outfile.println("Precision is, " + resultList.calPrecision() + " : ");
//
//			/*** Mining ***/ // train data
//				microClusterPairManager = realXMLPairSegmenter.scanfile(directory, list[i]);				
//				
//			}
//			
//			System.out.println("Likelihood_1 is: " + resultList.calLikelihood());
//			System.out.println("Likelihood_2 is: " + resultList.calculate1());
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
//}
