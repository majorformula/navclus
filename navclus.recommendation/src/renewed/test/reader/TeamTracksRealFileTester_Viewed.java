package renewed.test.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;

import renewed.evaluation.answer.TotalResults;
import renewed.in.reader.TeamTracksRealTxTSegmenter;
import renewed.lib.cluster.microclusters.TeamTracksMicroClusterManager;
import renewed.lib.recommender.TeamTracksTxTRecommender;

public class TeamTracksRealFileTester_Viewed {

	static String sampleDirectory = "D:/MylynData/TXT_PDE";
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

			TeamTracksMicroClusterManager microClusterManager = new TeamTracksMicroClusterManager();			
			for (int i = 0; i < list.length; i++) {
				
					// RECOMMENDATON
					if (i > 0) {
//						outfile.println(list[i]);
						(new TeamTracksTxTRecommender()).recommend(sampleDirectory, list[i], microClusterManager, totalResults);						
//						outfile.println(totalResults.calculatePrecision() + ", " + totalResults.calculateRecall());
					}
			
					// CLUSTERING
					microClusterManager = (new TeamTracksRealTxTSegmenter(microClusterManager)).scanfile(sampleDirectory, list[i]); //2
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