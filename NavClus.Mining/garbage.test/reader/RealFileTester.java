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
//import old.in.reader.RealXMLSegmenter;
//import old.lib.cluster.macroclusters.MacroClusterManager;
//import renewed.in.reader.SampleXMLSegmenter;
//import renewed.lib.cluster.microclusters.MicroClusterManager;
//
//
//
//
//public class RealFileTester {
//
//	static String trainDirectory = "H:/Data/C1-3Data";	
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
//		MicroClusterManager microClusterManager = (new RealXMLSegmenter()).scanfile(trainDirectory);
//		
////		// Print Micro-clusters	
////		microClusterManager.ignore(iThreshold);
////		microClusterManager.printKeys();
//		System.out.println("list size:" + microClusterManager.size());	
//		
//		// macro-clustering ... 1Â÷
//		MacroClusterManager macroClusterManager = new MacroClusterManager(microClusterManager);
//		macroClusterManager.cluster(0.2);
//				
////		// Print Macro-clusters
//		macroClusterManager.println(2);	// with sorting
//		
//	}
//}
