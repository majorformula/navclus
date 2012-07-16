package old.in.reader;

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

import renewed.in.reader.StructureHandlePrinter;
import renewed.lib.cluster.microclusters.MicroClusterManager;



public class RealXMLSegmenter {

	MicroClusterManager microClusterManager = new MicroClusterManager();
	
	public MicroClusterManager scanfile(String trainDirectory) {
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
			}
			return microClusterManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	MicroClusterManager scanfile(String trainDirectory, String file) {
		try {
			XMLInputFactory xmlif = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlif.createXMLEventReader(new FileReader(trainDirectory + "/" + file));
			XMLEvent event;
			while (reader.hasNext()) {
				event = reader.nextEvent();
				countSelectionEdit(event);
			}
			microClusterManager.insert2segment("done");
			return microClusterManager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	void countSelectionEdit(XMLEvent event) {
		
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

					microClusterManager.insert2segment(structureHandlePrinter.toElement(value2));
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

					microClusterManager.insert2segment(structureHandlePrinter.toElement(value2));	
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
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
