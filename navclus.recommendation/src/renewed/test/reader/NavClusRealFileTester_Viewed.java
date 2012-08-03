package renewed.test.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;

import renewed.evaluation.answer.EditHitTotalResults;
import renewed.evaluation.answer.TotalResults;
import renewed.in.reader.RealTxTSegmenter;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.microclusters.MicroVector;
import renewed.lib.recommender.NavClusTxTRecommender;
import renewed.lib.recommender.NavClusTxTRecommender_EditHit;

public class NavClusRealFileTester_Viewed {

	static String sampleDirectory = "D:/MylynData/TXT_MDT";
	static int iThreshold = 3 ;
	
	static TotalResults totalResults = new TotalResults();

	public static void main(String argv[])  {				
		try {
			FileOutputStream output;
			PrintStream outfile;
			
			output = new FileOutputStream("out.txt");
			outfile = new PrintStream(output);
			
			File file = new File(sampleDirectory); 
			String[] list = file.list(new FilenameFilter() 
			{ 
				@Override 
				public boolean accept(File dir, String name)  
				{ 
					return name.endsWith(".txt"); 
				} 
			}); 

			MicroClusterManager microClusterManager = new MicroClusterManager();	
			MacroClusterManager macroClusterManager = new MacroClusterManager();
			MacroClusterIndexer macroClusterIndexer = new MacroClusterIndexer();
			MicroVector microVector = new MicroVector();
			
			for (int i = 0; i < list.length - 1; i++) {						
					// MICRO-CLUSTERING
					microClusterManager = (new RealTxTSegmenter(microClusterManager)).scanfile(sampleDirectory, list[i]);
					microClusterManager.insert2segment("done"); // ... 각 파일 끝나는 정보...					
//					System.out.println();
					System.out.println("*****" + list[i] + "*****");
//					microClusterManager.printSummary();

					// Group micro-clusters...
					microVector.createClosestPair(microClusterManager);
					microVector.sort();
					//					microVector.println();
					microVector.group();
//					microVector.printGroup();
										
					// MACRO-CLUSTERING
					macroClusterIndexer = macroClusterManager.create(microClusterManager, microVector, macroClusterIndexer);
//					macroClusterIndexer = macroClusterManager.update(microClusterManager, 0.5, macroClusterIndexer);
//					macroClusterManager.printSummary();			
					
					// RECOMMENDATON
					(new NavClusTxTRecommender()).recommend(sampleDirectory, list[i+1], macroClusterIndexer, macroClusterManager, totalResults);						
//					outfile.println(totalResults.calculatePrecision() + ", " + totalResults.calculateRecall());
//					System.out.println("precision:	" + totalResults.calculatePrecision());
//					System.out.println("recall:		" + totalResults.calculateRecall());
//					System.out.println("#recommendations:	" + totalResults.size());
//										
					// clear
					microClusterManager.updateIntertedStatus();
					microVector.clear();
					macroClusterIndexer.clear();
					macroClusterManager.clear();
			}
			totalResults.printTotalResults(outfile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
}