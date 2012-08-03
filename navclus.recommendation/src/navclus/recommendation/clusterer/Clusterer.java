package navclus.recommendation.clusterer;

import java.io.File;
import java.io.FilenameFilter;

import renewed.in.reader.SampleXMLEditedSegmenter;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.microclusters.MicroVector;

public class Clusterer {

	public MacroClusterManager cluster(String dataDirectory) {
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
			MicroVector microVector = new MicroVector();

			for (int i = 0; i < 12; i++) {				
				// adding data
				microClusterManager = (new SampleXMLEditedSegmenter(microClusterManager)).scanfile(dataDirectory, list[i]);
				microClusterManager.insert2segment("done"); // ... 각 파일 끝나는 정보...
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
				macroClusterManager.create(microClusterManager, microVector);				
//				macroClusterManager.printSummary();
				
				return macroClusterManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}	
}
