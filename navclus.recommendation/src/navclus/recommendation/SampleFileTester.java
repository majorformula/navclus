/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.recommendation;

import java.io.File;
import java.io.FilenameFilter;

import renewed.evaluation.answer.TotalResults;
import renewed.in.reader.SampleXMLEditedSegmenter;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.cluster.microclusters.MicroClusterManager;
import renewed.lib.cluster.microclusters.MicroVector;

public class SampleFileTester {

	static String sampleDirectory = "D:\\MylynData\\Data\\Sample";

	static int iThreshold = 3 ;

	public static void main(String argv[])  {
		TotalResults totalResults = new TotalResults();

		try {
			File file = new File(sampleDirectory); 
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

			for (int i = 0; i < 11 /*list.length -1*/; i++) {				
				// adding data
				microClusterManager = (new SampleXMLEditedSegmenter(microClusterManager)).scanfile(sampleDirectory, list[i]);
				microClusterManager.insert2segment("done"); // ... 각 파일 끝나는 정보...

				System.out.println();
				System.out.println("*****" + list[i] + "*****");
				microClusterManager.printSummary();
//				microClusterManager.printKeys(2);

				// Group micro-clusters...
				microVector.createClosestPair(microClusterManager, 2);
				microVector.sort();
				microVector.println();
				microVector.group();
				microVector.printGroup();			

				// create macro-clusters		
				macroClusterIndexer = macroClusterManager.create(microClusterManager, microVector, macroClusterIndexer);
				macroClusterManager.printSummary();

				// creating recommendations ... 추천ㅇ을 발생시키고 있음
//				(new XMLSampleRecommender()).recommend(sampleDirectory, list[i+1], macroClusterIndexer, macroClusterManager, totalResults);

				//					macroClusterIndexer.print();
//									System.out.println(macroClusterIndexer.getNumberOfDocuments());
				//					macroClusterManager.printClusters();

				// clear
				microClusterManager.updateIntertedStatus();
				microVector.clear();
				macroClusterIndexer.clear();
				macroClusterManager.clear();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
