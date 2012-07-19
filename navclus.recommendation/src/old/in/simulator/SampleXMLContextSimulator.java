package old.in.simulator;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import renewed.data.elements.ElementManager;
import renewed.in.reader.StructureHandlePrinter;

public class SampleXMLContextSimulator {

	StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();
	
	Vector<ElementManager> contextManager = new Vector<ElementManager>();
	ElementManager currentContextManager;

	int i = 0;
	
	public Vector<ElementManager> scanfile(String trainDirectory, String file) {
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

			return contextManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void readEvents(NodeList nList) {
		currentContextManager = new ElementManager();


		for (int num = 0; num < nList.getLength(); num++) {

//			if (currentContextManager.size() >= 10) {
			if (i > 15) {
				segment();
				i = 0;
			}

			Node nNode = nList.item(num);	    
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				readEvent(eElement);
			}
		}
	}

	private void segment() {
		//				System.out.println(k++);
		contextManager.add(currentContextManager);
		currentContextManager = new ElementManager();		
	}

	private void readEvent(Element eElement) {
		if (("selection".equals(getTagValue("kind",eElement)))) { // ...Select 정보만 사용 ...
			//						|| ("edit".equals(getTagValue("kind",eElement)))){
			String name = (getTagValue("structureHandle",eElement));	
			currentContextManager.insert(structureHandlePrinter.toElement(name));
			i++;
		}
		else if (("edit".equals(getTagValue("kind",eElement)))) { // ...Edit 정보도  사용 ...
			//						|| ("edit".equals(getTagValue("kind",eElement)))){
			String name = (getTagValue("structureHandle",eElement));	
			currentContextManager.insert(structureHandlePrinter.toElement(name));
			i++;
		}
		
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0); 

		return nValue.getNodeValue();    
	}

}
