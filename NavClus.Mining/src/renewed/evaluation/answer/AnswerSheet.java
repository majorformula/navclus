package renewed.evaluation.answer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import renewed.in.reader.StructureHandlePrinter;

public class AnswerSheet {

	public static EditQueue answer(String directory, String file) {

		EditQueue editQueue = new EditQueue();

		try {

			BufferedReader in = new BufferedReader(new FileReader(directory + "/" + file));
			String strLine;

//			String prevElement = "";
			while ((strLine = in.readLine()) != null) {
				String[] strArray = strLine.split("/");

				String kind = strArray[0].trim();
				String date = strArray[1].trim();
				String endDate = strArray[2].trim();
				String element =  strArray[3].trim();

//				if (kind.equals("edit")) { 
					//				if (element.endsWith(".java") || element.endsWith(".class") ||  element.endsWith(".xml")) {
					//					continue;
					//				}
//					if (!element.equals(prevElement)) {
//					}
//					prevElement = element;
						
//					if (! date.equals(endDate)) {
						EditRecord editRecord = new EditRecord(date, element); 
						editQueue.add(editRecord);						
//					}
//				}
			}
			in.close();

			return editQueue;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}

}
