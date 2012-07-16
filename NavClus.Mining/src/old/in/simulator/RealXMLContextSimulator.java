package old.in.simulator;


import java.io.FileReader;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import renewed.data.elements.ElementManager;
import renewed.in.reader.StructureHandlePrinter;



public class RealXMLContextSimulator {
	
	Vector<ElementManager> contextManager = new Vector<ElementManager>();
	ElementManager currentContextManager;
	
	public Vector<ElementManager> scanfile(String trainDirectory, String file) {
		try {
			XMLInputFactory xmlif = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlif.createXMLEventReader(new FileReader(trainDirectory + "/" + file));
			XMLEvent event;
			
			int i = 0, k = 0;
			currentContextManager = new ElementManager();			
			while (reader.hasNext()) {
				if (i < 39) {
					event = reader.nextEvent();
					i = countSelectionEdit(event, i); // increase i
				}
				else {
//					System.out.println(k++);
					contextManager.add(currentContextManager);
					currentContextManager = new ElementManager(); // accumulate by removing this line...					
					i = 0;
				}
			}
			return contextManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	int countSelectionEdit(XMLEvent event, int i) {
		
		StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();

		if (event.isStartElement()) {
			StartElement element = (StartElement) event;
			//	          System.out.println("Start Element: " + element.getName());

			if (element.getName().toString().equals("InteractionEvent")) {		        	  
				Attribute attribute1 = element.getAttributeByName(new QName("Kind"));
				QName name1 = attribute1.getName();
				String value1 = attribute1.getValue();

				if (value1.equals("selection")) {		        	  
					Attribute attribute2 = element.getAttributeByName(new QName("StructureHandle"));
					QName name2 = attribute2.getName();
					String value2 = attribute2.getValue();

					//		        	  System.out.println("Attribute name/value: " + name2 + "/" + value2);
					//					if (!value2.contains("http://") && 
					//						!value2.contains(".jar"))
					//					out.println("select:" + StructureHandlePrinter.toElement(value2) + " ");

					currentContextManager.insert(structureHandlePrinter.toElement(value2));
//					System.out.println(structureHandlePrinter.toElement(value2)); // why "src&lt appeared?"
					i++;
					//					vectorManager.insert(StructureHandlePrinter.toElement(value2));	
					//					tmpSet.addtoPrev(value2);
					//					freqItems.add(value2);
				} else if (value1.equals("edit")) {		        	  
					Attribute attribute2 = element.getAttributeByName(new QName("StructureHandle"));
					QName name2 = attribute2.getName();
					String value2 = attribute2.getValue();

					//		        	  System.out.println("Attribute name/value: " + name2 + "/" + value2);
					//					iRule.addtoPrev(value2);
					//					currentSet.addtoPrev(value2);
					//					out.println("edit:" + StructureHandlePrinter.toElement(value2) + " ");

//					currentManager.insert(StructureHandlePrinter.toElement(value2));
//					i++;
					//					vectorManager.insert(StructureHandlePrinter.toElement(value2));	
					//					tmpSet.addtoNext(value2);
					//					freqItems.add(value2);
				}
			}		        
		}
		if (event.isEndElement()) {
			EndElement element = (EndElement) event;
			//	          System.out.println("End element:" + element.getName());
		}
		if (event.isCharacters()) {
			Characters characters = (Characters) event;
			//	          System.out.println("Text: " + characters.getData());
		}		
		return i;
	}

}
