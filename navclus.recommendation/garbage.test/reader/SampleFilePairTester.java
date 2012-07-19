//package old.test.reader;
//
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Vector;
//
//import old.in.reader.SampleXMLPairSegmenter;
//import old.lib.cluster.macroclusters.MacroClusterManager;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import renewed.in.reader.SampleXMLSegmenter;
//import renewed.lib.cluster.microclusters.MicroClusterManager;
//
//
//
//
//public class SampleFilePairTester {
//
//	static String trainDirectory = "H:/Data/Sample-Data";	
//	
//	static int iThreshold = 7 ;
//
//	public static void main(String argv[])  {		
//		// read file names
//		File file = new File(trainDirectory); 
//		String[] list = file.list(new FilenameFilter() 
//		{ 
//			@Override 
//			public boolean accept(File dir, String name)  
//			{ 
//				return name.endsWith(".xml"); 
//			} 
//		}); 
//			
//		// Micro-clustering
//		MicroClusterPairManager microClusterPairManager = (new SampleXMLPairSegmenter()).scanfile(trainDirectory);
////		microClusterPairManager.printPair();
//		
////		// Print Micro-clusters	
//		microClusterPairManager.ignore(iThreshold);
////		microClusterPairManager.printKeys();
//		System.out.println("list size:" + microClusterPairManager.size());	
//		
////		// macro-clustering ... 1Â÷
//		MacroClusterPairManager macroClusterManager = new MacroClusterPairManager(microClusterPairManager);
//		macroClusterManager.cluster(0.2);
////				
//////		// Print Macro-clusters
//		macroClusterManager.println(3);	// with sorting
//		
//	}
//	
//}
