/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.recommendation.tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import navclus.recommendation.clusterer.Clusterer;
import navclus.recommendation.recommender.XMLSampleRecommender;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import renewed.data.elements.ElementManager;
import renewed.in.reader.StructureHandlePrinter;
import renewed.invertedindex.DocListNode;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.invertedindex.SortedLinkedList;
import renewed.lib.cluster.macroclusters.MacroClusterManager;
import renewed.lib.recommender.StringQueue;

public class SampleFileTester {	
	static String dataDirectory = "D:\\MylynData\\Data\\Sample";

	static int iThreshold = 3 ;

	public static void main(String argv[])  {		
		// cluster navigation sequences...
		MacroClusterManager macroClusterManager = (new Clusterer()).cluster(dataDirectory);
		
		// creating recommendations ... 추천ㅇ을 발생시키고 있음
		simulateRecommendations(dataDirectory, "s12-monitor-history.xml", macroClusterManager);		
	}
	
	public static void simulateRecommendations(String directory, String file, MacroClusterManager macroClusterManager) {
		ElementManager recommendation = new ElementManager();
		
		// 컨텍스트를 위한 자료 구조를 만든다.	
		int nContext = 3; 
		StringQueue contextQueue = new StringQueue(nContext); 
		StructureHandlePrinter shp = new StructureHandlePrinter();

		// 파일을 읽으면서 일정 컨텍스트가 형성되는지 확인한다.
		boolean bRecommend = false;
		try {
			File fXmlFile = new File(directory + "/" + file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("interactionEvent");
//			System.out.println("-----------------------");

			// loop in this method
			for (int num = 0; num < nList.getLength(); num++) {
				Node nNode = nList.item(num);	    
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (("selection".equals(getTagValue("kind",eElement)))) { // ...Select 정보만 사용 ...
						//						|| ("edit".equals(getTagValue("kind",eElement)))){
						String value2 = (getTagValue("structureHandle",eElement));	
						bRecommend = contextQueue.add(shp.toElement(value2));
					}
					else if (("edit".equals(getTagValue("kind",eElement)))) { // ...Edit 정보도  사용 ...
						//						|| ("edit".equals(getTagValue("kind",eElement)))){
						String value2 = (getTagValue("structureHandle",eElement));	
						String value2_1 = (getTagValue("date",eElement));
						String value2_2 = (getTagValue("endDate",eElement));		

						if (!value2_2.equals(value2_1)) {
							bRecommend = contextQueue.add(shp.toElement(value2));
						}
						else {
							bRecommend = contextQueue.add(shp.toElement(value2));
						}
					}
				}				
				// make a query
				if ((contextQueue.size() >= nContext) && (bRecommend == true)) {	
					// making a recommendation			
					recommendation = (new XMLSampleRecommender()).recommend(contextQueue, macroClusterManager);		
					recommendation.println(2, 10);
					
					bRecommend = false;
					contextQueue.clear();
					recommendation.clear();
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0); 

		return nValue.getNodeValue();    
	}
}
