package renewed.in.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

import renewed.lib.cluster.microclusters.TeamTracksMicroClusterManager;

public class TeamTracksRealTxTSegmenter {

	TeamTracksMicroClusterManager TeamTracksMicroClusterManager; // to be deleted
	int n = 2;

	public TeamTracksRealTxTSegmenter(TeamTracksMicroClusterManager TeamTracksMicroClusterManager) {
		this.TeamTracksMicroClusterManager = TeamTracksMicroClusterManager;
	}
	
	public TeamTracksMicroClusterManager scanfile(String trainDirectory, String file, int tn) {
		this.n = tn;
		
		try {			
			BufferedReader in = new BufferedReader(new FileReader(trainDirectory + "/" + file));
			String strLine;						
			
			while ((strLine = in.readLine()) != null) {							
				String[] strArray = strLine.split("/");				
				String kind = strArray[0].trim();
				String date = strArray[1].trim();
				String endDate = strArray[2].trim();
				String element =  strArray[3].trim();
				
				// 날짜가 다른  edit만 수정...
				if (kind.equals("edit") && date.equals(endDate)) {
//					TeamTracksMicroClusterManager.insert2segment(element, "select");	
				}
				else {
					TeamTracksMicroClusterManager.insert2segment(element, kind, n);
				}
			}
			in.close();
			
			
			return TeamTracksMicroClusterManager;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TeamTracksMicroClusterManager scanfile(String trainDirectory, String file) {	
		try {			
			BufferedReader in = new BufferedReader(new FileReader(trainDirectory + "/" + file));
			String strLine;						
			
			while ((strLine = in.readLine()) != null) {							
				String[] strArray = strLine.split("/");				
				String kind = strArray[0].trim();
				String date = strArray[1].trim();
				String endDate = strArray[2].trim();
				String element =  strArray[3].trim();
				
				// 날짜가 다른  edit만 수정...
				if (kind.equals("edit") && date.equals(endDate)) {
					TeamTracksMicroClusterManager.insert2segment(element, "select", 2);	// disabled --> enabled
				}
				else {
					TeamTracksMicroClusterManager.insert2segment(element, kind, 2);
				}
			}
			in.close();
						
			return TeamTracksMicroClusterManager;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
