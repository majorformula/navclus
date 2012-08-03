package navclus.recommendation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import renewed.in.reader.StructureHandlePrinter;
import renewed.invertedindex.DocListNode;
import renewed.invertedindex.MacroClusterIndexer;
import renewed.invertedindex.SortedLinkedList;
import renewed.lib.recommender.StringQueue;


public class XMLSampleRecommender {
	StructureHandlePrinter shp = new StructureHandlePrinter();
	
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0); 

		return nValue.getNodeValue();    
	}

	public void recommend(String directory, String file, MacroClusterIndexer macroClusterIndexer) {		
		// 컨텍스트를 위한 자료 구조를 만든다.	
		int nContext = 3; 
		StringQueue contextQueue = new StringQueue(nContext); 

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
					Set<String> contextVisitSet = contextQueue.toSet();
					SortedLinkedList<DocListNode> answer = retrieve(contextQueue, macroClusterIndexer);
					
					bRecommend = false;
					contextQueue.clear();
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
	
	public SortedLinkedList<DocListNode> retrieve(StringQueue contextQueue, MacroClusterIndexer macroClusterIndexer) {
		SortedLinkedList<DocListNode> answer = macroClusterIndexer.retrieve(contextQueue.getStringQueue());
		print("retrieve answer", answer);
		return answer;
	}
	
	public void print(String title, SortedLinkedList<DocListNode> p) {
		if (p == null) return;
		
		System.out.print(title +": ");
		for (int i = 0; i < p.size(); i++) {	
			System.out.print(p.get(i) + ", ");	
		}
		System.out.println();
	}
	
	public void print(Set<String> contextVisitSet) {
		System.out.print("Query: ");
		for (String e: contextVisitSet) {
			System.out.print(e + ", ");
		}
		System.out.println();
	}

}