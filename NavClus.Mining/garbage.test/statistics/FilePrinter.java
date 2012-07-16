//// http://www.javapractices.com/topic/TopicAction.do?Id=42
//
//package old.test.statistics;
//
//
//import java.io.File;
//import java.io.FilenameFilter;
//import java.io.PrintWriter;
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
////import old.out.recommender.RealRecommender;
//import old.out.recommender.SampleRecommender;
//import renewed.data.elements.ElementManager;
//import renewed.in.reader.SampleXMLSegmenter;
//import renewed.lib.cluster.microclusters.MicroClusterManager;
//
//
//
//
//
//public class FilePrinter {
//
//	static String trainDirectory = "E:/Project_Platform"; 	
//	
////	static String trainDirectory = "H:/Data/C1-3Data"; 	
////	static String testDirectory = "H:/Data/C1-3Test"; // Similarity = 0.075
//	
////	static String trainDirectory = "H:/Data/Platform-Data";	
////	static String testDirectory = "H:/Data/Platform"; // Similarity = 0.02
//	static int iThreshold = 0 ; // 샘플에서는 7이었는데 이를 실제 데이타에서는 3으로 낮추었다.
//	
//	public static void main(String[] args) {		
//		try {
//		    PrintWriter out = new PrintWriter("a.txt");
//			
//			
//			// train data
//			File file = new File(trainDirectory); 
//			String[] list = file.list(new FilenameFilter() 
//			{ 
//				@Override 
//				public boolean accept(File dir, String name)  
//				{ 
//					return name.endsWith(".xml"); 
//				} 
//			}); 
//
//			for (int i = 0; i < list.length; i++) {
//					out.println(list[i]);
////					scanfile(trainDirectory, list[i]);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
//	}
//}
