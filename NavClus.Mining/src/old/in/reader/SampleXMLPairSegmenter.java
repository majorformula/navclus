package old.in.reader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import old.lib.cluster.microclusters.MicroClusterPairManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import renewed.in.reader.StructureHandlePrinter;



public class SampleXMLPairSegmenter {

	MicroClusterPairManager microClusterPairManager = new MicroClusterPairManager();

	public MicroClusterPairManager scanfile(String trainDirectory) {
		try {
			// train data
			File file = new File(trainDirectory); 
			String[] list = file.list(new FilenameFilter() 
			{ 
				@Override 
				public boolean accept(File dir, String name)  
				{ 
					return name.endsWith(".xml"); 
				} 
			}); 

			for (int i = 0; i < list.length; i++) {
				//					System.out.println(list[i] + ": " );
				scanfile(trainDirectory, list[i]);
				microClusterPairManager.insert2visit("done"); // ... 각 파일 끝나는 정보...
			}
			return microClusterPairManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public MicroClusterPairManager scanfile(String trainDirectory, String file) {
		try {			
			File fXmlFile = new File(trainDirectory + "/" + file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("interactionEvent");
			//			System.out.println("-----------------------");
			readElement(nList);

			return microClusterPairManager;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void readElement(NodeList nList) {
		StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();

		int i = 0;

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);	    
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				if (("selection".equals(getTagValue("kind",eElement)))) { // ...Select + Edit 정보 사용 ...				
					String name = (getTagValue("structureHandle",eElement));		
					microClusterPairManager.insert2visit(structureHandlePrinter.toElement(name));
				}
				else if (("edit".equals(getTagValue("kind",eElement)))) { // ...Edit + Edit 정보 사용 ...					
					String name = (getTagValue("structureHandle",eElement));
					microClusterPairManager.insert2change(structureHandlePrinter.toElement(name));
				}
			}
		}
	}

	private String getTagValue(String sTag, Element eElement){
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0); 

		return nValue.getNodeValue();    
	}

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
