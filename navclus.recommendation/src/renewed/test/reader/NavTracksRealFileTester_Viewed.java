package renewed.test.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;

import renewed.evaluation.answer.TotalResults;
import renewed.in.reader.RealTxTSegmenter;
import renewed.in.reader.TeamTracksRealTxTSegmenter;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.microclusters.TeamTracksMicroClusterManager;
import renewed.lib.recommender.NavTracksTxTRecommender;
import renewed.lib.recommender.TeamTracksTxTRecommender;
import renewed.lib.recommender.NavClusTxTRecommender;

public class NavTracksRealFileTester_Viewed {

	static String sampleDirectory = "D:/MylynData/TXT_Mylyn";
//	static int iThreshold = 3 ;
	
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
			for (int i = 0; i < list.length; i++) {
				
					// RECOMMENDATON
					if (i > 0) {
//						outfile.println(list[i]);
						(new NavTracksTxTRecommender()).recommend(sampleDirectory, list[i], microClusterManager, totalResults);						
						outfile.println(totalResults.calculatePrecision() + ", " + totalResults.calculateRecall());
					}
			
					// CLUSTERING
					microClusterManager = (new RealTxTSegmenter(microClusterManager)).scanfile(sampleDirectory, list[i]); //2
					microClusterManager.insert2segment("done"); // ... 각 파일 끝나는 정보...					
//					System.out.println("*****" + list[i] + "*****");
//					microClusterManager.printSummary();										
														
					// clear
					microClusterManager.clearStatus();
			}			
//			totalResults.printTotalResults(outfile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}