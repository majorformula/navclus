package renewed.in.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import renewed.lib.cluster.microclusters.MicroClusterManager;

public class RealTxTSegmenter {
	
	MicroClusterManager microClusterManager; // to be deleted

	public RealTxTSegmenter(MicroClusterManager microClusterManager) {
		this.microClusterManager = microClusterManager;
	}
	
	public MicroClusterManager scanfile(String trainDirectory, String file) {
		try {
			
			BufferedReader in = new BufferedReader(new FileReader(trainDirectory + "/" + file));
			String strLine;
						
//			File fXmlFile = new File(trainDirectory + "/" + file);
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(fXmlFile);
//			doc.getDocumentElement().normalize();
//
////			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//			NodeList nList = doc.getElementsByTagName("interactionEvent");
////			System.out.println("-----------------------");
//			readElement(nList);
			
			while ((strLine = in.readLine()) != null) {							
				String[] strArray = strLine.split("/");				
				String kind = strArray[0].trim();
				String date = strArray[1].trim();
				String endDate = strArray[2].trim();
				String element =  strArray[3].trim();
				
				// 날짜가 다른  edit만 수정...
				if (kind.equals("edit") && date.equals(endDate)) {
//					microClusterManager.insert2segment(element, "select");	
				}
				else {
					microClusterManager.insert2segment(element, kind);
				}
			}
			in.close();
			
			
			return microClusterManager;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	private void readElement(NodeList nList) {
//		StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();
//		
//		int i = 0;
//		
//		for (int temp = 0; temp < nList.getLength(); temp++) {
//
//			Node nNode = nList.item(temp);	    
//			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//
//				Element eElement = (Element) nNode;
//
//				if (("selection".equals(getTagValue("kind",eElement)))  // ...Select 정보만 사용 ...
//				|| ("edit".equals(getTagValue("kind",eElement)))){
//					
//					String name = (getTagValue("structureHandle",eElement));		
//					microClusterManager.insert2segment(structureHandlePrinter.toElement(name));					
//				}
//			}
//		}
//	}
	
//	private String getTagValue(String sTag, Element eElement){
//		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
//		Node nValue = (Node) nlList.item(0); 
//
//		return nValue.getNodeValue();    
//	}
	
	private boolean calculateDifference(Date previousDate, Date currentDate) {
		if (previousDate == null) {
			return false;
		}
		if (currentDate == null) {
			return false;
		}

		Long difference = currentDate.getTime() - previousDate.getTime();

		// Long value : 1000 miliseconds * 60 seconds * 2 (more than 3 minutes...) = 120,000
		if (difference > 120000) {
			return true;
		} else {
			return false;
		}
	}

}
