package navclus.recommendation;

import java.io.File;
import java.io.FilenameFilter;

import renewed.in.reader.SampleXMLEditedSegmenter;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.microclusters.MicroVector;

public class Clusterer {

	static String dataDirectory = "D:\\MylynData\\Data\\Sample";

	static int iThreshold = 3 ;

	public static void main(String argv[])  {
		
		// cluster navigation sequences...
		MacroClusterIndexer macroClusterIndexer = cluster(dataDirectory);
		
		// creating recommendations ... ��õ���� �߻���Ű�� ����
		(new XMLSampleRecommender()).recommend(dataDirectory, "s12-monitor-history.xml", macroClusterIndexer);
		
/*
				//					macroClusterIndexer.print();
//									System.out.println(macroClusterIndexer.getNumberOfDocuments());
				//					macroClusterManager.printClusters();			
*/
	}
	
	public static MacroClusterIndexer cluster(String dataDirectory) {
		try {
			File file = new File(dataDirectory); 
			String[] list = file.list(new FilenameFilter() 
			{ 
				@Override 
				public boolean accept(File dir, String name)  
				{ 
					return name.endsWith(".xml"); 
				} 
			}); 

			MicroClusterManager microClusterManager = new MicroClusterManager();	
			MacroClusterManager macroClusterManager = new MacroClusterManager();
			MacroClusterIndexer macroClusterIndexer = new MacroClusterIndexer();
			MicroVector microVector = new MicroVector();

			for (int i = 0; i < 12; i++) {				
				// adding data
				microClusterManager = (new SampleXMLEditedSegmenter(microClusterManager)).scanfile(dataDirectory, list[i]);
				microClusterManager.insert2segment("done"); // ... �� ���� ������ ����...
			}
//			microClusterManager.printSummary();
//			microClusterManager.printKeys(2);
			
				// Group micro-clusters...
				microVector.createClosestPair(microClusterManager, 2);
				microVector.sort();
//				microVector.println();
				microVector.group();
//				microVector.printGroup();			

				// create macro-clusters		
				macroClusterIndexer = macroClusterManager.create(microClusterManager, microVector, macroClusterIndexer);				
//				macroClusterManager.printSummary();
				
				return macroClusterIndexer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}	
}
