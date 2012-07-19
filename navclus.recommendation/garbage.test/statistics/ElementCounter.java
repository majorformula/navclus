package old.test.statistics;


import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

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


public class ElementCounter {

	ElementManager totalManager = new ElementManager();
	ElementManager currentManager = new ElementManager();
	
	int totalElementNo = 0;
	int totalFileNo = 0;
	
	int SumOfTraceElementNo = 0;
	int SumOfTraceFileNo = 0;
	
	int FileNo = 0;
	int TraceNo = 0;

	public void scanfile(String trainDirectory) {
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
				TraceNo++;
				scanfile(trainDirectory, list[i]);
			}
			
			totalElementNo = totalManager.size();
			totalFileNo = totalManager.countFiles();
			
			System.out.println("viewed files              :" + (float) totalFileNo );
			System.out.println("viewed elements           :" + (float) totalElementNo );
			System.out.println("averaged viewed files     :" + (float) SumOfTraceFileNo / (float) TraceNo);
			System.out.println("averaged viewed elements  :" + (float) SumOfTraceElementNo / (float) TraceNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void scanfile(String trainDirectory, String file) {
		try {
			XMLInputFactory xmlif = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlif.createXMLEventReader(new FileReader(trainDirectory + "/" + file));
			XMLEvent event;
			while (reader.hasNext()) {
				event = reader.nextEvent();
				countSelectionEdit(event);
			}

			//			if (elementManager.size() > 0) {
			//				copyFile.copyfile(trainDirectory + "/" + file, "H:/Data/Platform-Cleaned" + "/" + file);
			//			}			
			System.out.println(file + ": " + currentManager.size() + "	" + currentManager.countFiles());
			
			SumOfTraceFileNo += currentManager.countFiles();
			SumOfTraceElementNo += currentManager.size();
			currentManager.clear();			
		} catch (Exception e) {
			e.printStackTrace();
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

					currentManager.insert(structureHandlePrinter.toElement(value2));	
					totalManager.insert(structureHandlePrinter.toElement(value2));	

				} else if (value1.equals("edit")) {		        	  
					Attribute attribute2 = element.getAttributeByName(new QName("StructureHandle"));
					QName name2 = attribute2.getName();
					String value2 = attribute2.getValue();					

					Attribute attribute2_1 = element.getAttributeByName(new QName("StartDate"));
					QName name2_1 = attribute2_1.getName();
					String value2_1 = attribute2_1.getValue();

					Attribute attribute2_2 = element.getAttributeByName(new QName("EndDate"));
					QName name2_2 = attribute2_2.getName();
					String value2_2 = attribute2_2.getValue();

					if (!value2_2.equals(value2_1)) {
//						currentManager.insert(structureHandlePrinter.toElement(value2));	
//						totalManager.insert(structureHandlePrinter.toElement(value2));	
					}
					
					
				}
			}		        
		}
		if (event.isEndElement()) {
			EndElement element = (EndElement) event;
		}
		if (event.isCharacters()) {
			Characters characters = (Characters) event;
		}		
	}

}
