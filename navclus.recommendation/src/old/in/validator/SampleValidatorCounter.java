package old.in.validator;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import renewed.data.elements.ElementManager;
import renewed.in.reader.StructureHandlePrinter;



public class SampleValidatorCounter {
	
	StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();
	ElementManager answerManager = new ElementManager();	

	public ElementManager scanfile(String trainDirectory, String file) {
		try {
			File fXmlFile = new File(trainDirectory + "/" + file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("interactionEvent");
//			System.out.println("-----------------------");

			// loop in this method
			readEvents(nList);

			return answerManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void readEvents(NodeList nList) {
		for (int num = 0; num < nList.getLength(); num++) {
			Node nNode = nList.item(num);	    
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				readEvent(eElement);
			}
		}
	}

	private void readEvent(Element eElement) {
		if (("edit".equals(getTagValue("kind",eElement)))) { // ...edit 정보만 사용 ...
			String name = (getTagValue("structureHandle",eElement));	
			answerManager.insert(structureHandlePrinter.toElement(name));
		}
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0); 
		return nValue.getNodeValue();    
	}

}
