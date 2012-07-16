//package old.out.recommender;
//
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FilenameFilter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//import javax.xml.namespace.QName;
//import javax.xml.stream.XMLEventReader;
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.Attribute;
//import javax.xml.stream.events.Characters;
//import javax.xml.stream.events.EndElement;
//import javax.xml.stream.events.StartElement;
//import javax.xml.stream.events.XMLEvent;
//
//import old.data.pairs.ElementPairManager;
//import old.in.reader.StructureHandlePrinter;
//import old.in.simulator.RealXMLContextSimulator;
//import old.in.validator.Validator;
//import old.lib.cluster.macroclusters.MacroClusterPairManager;
//import old.lib.cluster.microclusters.MicroClusterPairManager;
//import old.lib.similarity.CosineSimilarityCalculator;
//import renewed.data.elements.Element;
//import renewed.data.elements.ElementManager;
//
//
//
//public class RealAccumulatedRecommender {
//
//	ElementManager currentContextManager;
//	int numQueries = 0;
//	
//	double similarityThreshold;
//
//	public RealAccumulatedRecommender(double similarityThreshold) {
//		this.similarityThreshold = similarityThreshold;
//	}
//
//	/**
//	 * run all test files ...
//	 */
//	public void run(String testDirectory, MacroClusterPairManager clusterPairManager) {
//
//		try {
//
//			File file = new File(testDirectory);		
//			String[] list = file.list(new FilenameFilter() 
//			{ 
//				@Override 
//				public boolean accept(File dir, String name)  
//				{ 
//					return name.endsWith(".xml"); 
//				} 
//			});		
//
//			/*** Recommending Data ***/
//			ResultList resultList = new ResultList();
//
//			// loop for each file ...
//
//			for (int i = 0; i <list.length; i++) { 
//
//				/*** Creating a Validator per each file ***/
//				Validator validator = new Validator(testDirectory, list[i]);					
//
//				/*** Making a recommendation per each file ***/
//				ResultFair result = checkRecommend(testDirectory, list[i], clusterPairManager, validator);
//				resultList.add(result);				
//			}
//
//			System.out.println("#queries is: " + numQueries);
//			System.out.println("#recommendations is: " + resultList.sum());
//			System.out.println("Likelihood is: " + resultList.calLikelihood());
//			System.out.println("Precision is: " + resultList.calPrecision());
//			System.out.println("Recall is: " + resultList.calRecall());
//			System.out.println("Feedback is: " + (double) resultList.sum() / (double) numQueries);
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * run each test files ...
//	 * @throws XMLStreamException 
//	 * @throws FileNotFoundException 
//	 */
//	public ResultFair checkRecommend(
//			String testDirectory, 
//			String file, 
//			MacroClusterPairManager clusterPairManager, 
//			Validator validator) throws FileNotFoundException, XMLStreamException {
//
//		int iTrue =0, iFalse = 0;
//		double sumOfPrecision = 0, sumOfRecall = 0;
//
//		/*** Creating a Context per each file ***/
//		//		Vector<ElementManager> contextManagers;					
//		//		contextManagers = (new RealXMLContextSimulator()).scanfile(testDirectory, file);
//		//		numQueries = numQueries + contextManagers.size();
//		XMLInputFactory xmlif = XMLInputFactory.newInstance();
//		XMLEventReader reader = xmlif.createXMLEventReader(new FileReader(testDirectory + "/" + file));
//		XMLEvent event;
//
//		//		this.scanfile(reader); // fillup contextManager ...
//
//		// for each context
//		//		for (ElementManager context: contextManagers) { // 하나의 파일임...
//
//		//		while (currentContextManager != null) {
//
//		int i = 0, k = 0;
//		currentContextManager = new ElementManager();		
//		while (reader.hasNext()) {
//			if (i < 10) {
//				event = reader.nextEvent();
//				i = countSelectionEdit(event, i); // increase i
////				System.out.println(i);
//			}
//			else {
//				numQueries++;
//				// recommend per each context
////				System.out.println("recommend?");
//				ElementManager changeManager = recommend(currentContextManager, clusterPairManager);
////				System.out.println("recommend!");
//
//				// validate
//				if (changeManager == null) {
//					i = 0;
////					currentContextManager = new ElementManager(); //... for an accumulation of a context
//					event = reader.nextEvent();
//					i = countSelectionEdit(event, i); // increase i
//					continue;
//				}
//
//				boolean bDecision = validator.validate(changeManager);
//				sumOfPrecision = sumOfPrecision + validator.calPrecision(changeManager);
//				sumOfRecall = sumOfRecall + validator.calRecall(changeManager);
//				if (bDecision) {
//					iTrue++;
//
//				}
//				else {
//					iFalse++;
//
//					//					System.out.println("Validator's Changes");
//					//					validator.printChanges();		
//					//					
//					//					System.out.println("context:");
//					//					contextManager.sort();
//					//					contextManager.printHashMap();
//					//	
//					//					// visit manager		
//					//					visitFileManager.sort();	
//					//					System.out.println("visit:");
//					//					visitFileManager.printHashMap(); 
//					//	
//					//					// change manager
//					//					System.out.println("change:");						
//					//					changeManager.println();
//				}
//
////				this.scanfile(reader); // fillup contextManager ...
//				i = 0;
////				currentContextManager = new ElementManager(); //... for an accumulation of a context
//				event = reader.nextEvent();
//				i = countSelectionEdit(event, i); // increase i
//
//			}
//		}
//		//		}
//		System.out.println(file + ": #False:" + iFalse + "	#True:" + iTrue + "		" + iTrue / (float)(iTrue + iFalse)+ "     precision: " + sumOfPrecision / ((double)(iTrue + iFalse)) + "		recall:" + sumOfRecall / ((double)(iTrue + iFalse)));
//		return new ResultFair(iFalse, iTrue, sumOfPrecision, sumOfRecall);
//	}
//	
//	
//	/**
//	 * run each test files ...
//	 * @throws XMLStreamException 
//	 * @throws FileNotFoundException 
//	 */
//	public ResultFair checkRecommend(
//			String testDirectory, 
//			String file, 
//			MicroClusterPairManager clusterPairManager, 
//			Validator validator) throws FileNotFoundException, XMLStreamException {
//
//		int iTrue =0, iFalse = 0;
//		double sumOfPrecision = 0, sumOfRecall = 0;
//		int iQueries = 0;
//
//		/*** Creating a Context per each file ***/
//		//		Vector<ElementManager> contextManagers;					
//		//		contextManagers = (new RealXMLContextSimulator()).scanfile(testDirectory, file);
//		//		numQueries = numQueries + contextManagers.size();
//		XMLInputFactory xmlif = XMLInputFactory.newInstance();
//		XMLEventReader reader = xmlif.createXMLEventReader(new FileReader(testDirectory + "/" + file));
//		XMLEvent event;
//
//		//		this.scanfile(reader); // fillup contextManager ...
//
//		// for each context
//		//		for (ElementManager context: contextManagers) { // 하나의 파일임...
//
//		//		while (currentContextManager != null) {
//
//		int i = 0, k = 0;
//		currentContextManager = new ElementManager();		
//		while (reader.hasNext()) {
//			if (i < 5) { // 축적...
//				event = reader.nextEvent();
//				i = countSelectionEdit(event, i); // increase i
////				System.out.println(i);
//			}
//			else {
//				numQueries++;
//				iQueries++;
//				// recommend per each context
////				System.out.println("recommend?");
//				ElementManager changeManager = recommend(currentContextManager, clusterPairManager);
////				System.out.println("recommend!");
//
//				// validate
//				if (changeManager == null) {
//					i = 0;
////					currentContextManager = new ElementManager(); //... for an accumulation of a context
//					event = reader.nextEvent();
//					i = countSelectionEdit(event, i); // increase i
//					continue;
//				}
//
//				boolean bDecision = validator.validate(changeManager);
//				sumOfPrecision = sumOfPrecision + validator.calPrecision(changeManager);
//				sumOfRecall = sumOfRecall + validator.calRecall(changeManager);
//				if (bDecision) {
//					iTrue++;
//
//				}
//				else {
//					iFalse++;
//
//					//					System.out.println("Validator's Changes");
//					//					validator.printChanges();		
//					//					
//					//					System.out.println("context:");
//					//					contextManager.sort();
//					//					contextManager.printHashMap();
//					//	
//					//					// visit manager		
//					//					visitFileManager.sort();	
//					//					System.out.println("visit:");
//					//					visitFileManager.printHashMap(); 
//					//	
//					//					// change manager
//					//					System.out.println("change:");						
//					//					changeManager.println();
//				}
//
////				this.scanfile(reader); // fillup contextManager ...
//				i = 0;
////				currentContextManager = new ElementManager(); //... for an accumulation of a context
//				event = reader.nextEvent();
//				i = countSelectionEdit(event, i); // increase i
//
//			}
//		}
//		//		}
//		System.out.println(file + ": #False:" + iFalse + "	#True:" + iTrue + "		" + iTrue / (float)(iTrue + iFalse)+ "     precision: " + sumOfPrecision / ((double)(iTrue + iFalse)) + "		recall:" + sumOfRecall / ((double)(iTrue + iFalse)));
//		return new ResultFair(iFalse, iTrue, sumOfPrecision, sumOfRecall, iQueries);
//	}
//	
//
//	public ElementManager recommend(ElementManager context, MacroClusterPairManager clusterPairManager) {
//
//		List<String> recommendationkeys = recommendKey(context, clusterPairManager);
//
//		// recommend 1, 2, 3
//		if (recommendationkeys == null) {
//			return null;
//		}
//
//		ElementPairManager microCluster = new ElementPairManager();
//
//		for (String key: recommendationkeys) {
//			microCluster.merge(clusterPairManager.getMicroClusterManager().get(key));					
//		}								
//
//		// 이게 추천 결과임
//		int minSupport = 0;
//		ElementManager changeManager = microCluster.getChangeManager(minSupport); 
//		if (changeManager.size() < 1) { 
//			return null;
//		}
//
//		// weighting and ranking!
//
//		// forming a context for a ranking
//		context.copyPackage2HashMap();
//
//		ElementManager visitFileManager = microCluster.getVisitFileManager(); // 이게 추천 결과임
//		visitFileManager.copyFile2HashMap();				
//
//		changeManager.weight(context, visitFileManager);
//		changeManager.rank();
//		//			changeManager.println();
//		return changeManager;
//	}
//	
//	public ElementManager recommend(ElementManager context, MicroClusterPairManager clusterPairManager) {
//
//		List<String> recommendationkeys = recommendKey(context, clusterPairManager);
//
//		// recommend 1, 2, 3
//		if (recommendationkeys == null) {
//			return null;
//		}
//
//		ElementPairManager microCluster = new ElementPairManager();
//
//		for (String key: recommendationkeys) {
//			microCluster.merge(clusterPairManager.get(key));					
//		}								
//
//		// 이게 추천 결과임
//		int minSupport = 0;
//		ElementManager changeManager = microCluster.getChangeManager(minSupport); 
//		if (changeManager.size() < 1) { 
//			return null;
//		}
//
//		// weighting and ranking!
//
//		// forming a context for a ranking
//		context.copyPackage2HashMap();
//
//		ElementManager visitFileManager = microCluster.getVisitFileManager(); // 이게 추천 결과임
//		visitFileManager.copyFile2HashMap();				
//
//		changeManager.weight(context, visitFileManager);
//		changeManager.rank();
//		//			changeManager.println();
//		return changeManager;
//	}
//
//	public List<String> recommendKey(ElementManager contextManager, MacroClusterPairManager clusterPairManager) {
//		double max_value = 0;
//		List<String> max_keys = new ArrayList<String>();
//		//		ElementManager keyManager = new ElementManager();
//
//		// cosine similarity calculator
//		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
//
//		for (String keyElement1: clusterPairManager.getMicroClusterManager().keySet()) {
//			ElementPairManager microCluster = clusterPairManager.getMicroClusterManager().get(keyElement1);
//
//			/*** 해당 부분에서 False 추천이 줄어듬 ***/
//			if (!keyElement1.contains(".java"))
//				continue;			
//
//			//			if (microCluster.getKeyNum() < 2) // size를 올리면 정확도 높아짐...
//			//				continue;
//
//			//			if (contextManager.find(keyElement1) == null)
//			//				continue;
//
//			if (microCluster.getChangeManager().size() <= 2) // size를 올리면 정확도 높아짐...
//				continue;
//			if (microCluster.getVisitManager().size() <= 3) // size를 올리면 정확도 높아짐...
//				continue;
//
//			double value = calculator.CosineSimilarity(contextManager, microCluster.getVisitManager());
//			if (value > similarityThreshold) {
//				//				System.out.println(value);
//
//				// ignore if it is a creator
//				if (keyElement1.indexOf(".java") == -1)
//					continue;
//				if (keyElement1.indexOf(",") == -1)
//					continue;
//
//				if (keyElement1.indexOf(",") + 2 >= keyElement1.indexOf(".java"))
//					continue;
//
//				String filename = keyElement1.substring(keyElement1.indexOf(",") + 2, keyElement1.indexOf(".java"));
//				if ((keyElement1.lastIndexOf(filename)+ filename.length()) == (keyElement1.length()))
//					continue;
//				//					System.out.println("it is a creator function");
//
//				//			if (value > max_value ) { // && (keyElement1.contains(".java"))){ // remove the package level
//				//			if ((value > max_value ) && (keyElement1.contains(".java"))){ // remove the package level
//				if (value > max_value)
//					max_value = value;
//
//				//				keyManager.add(new Element(keyElement1, value));
//				max_keys.add(keyElement1);
//				//						System.out.print("num: " + ++ num + "  ");
//
//			}
//		}
//
//		//		keyManager.sortSimilarity();
//		//		int length = 5;
//		//		if (keyManager.size() < 5)
//		//			length = keyManager.size();
//		//		
//		//		for (int i = 0; i < length; i++) {
//		//			String key = keyManager.elementAt(i).getName();			
//		//			max_keys.add(key);
//		//		}		
//
//		/*** 해당 부분에서 False 추천이 줄어듬 ***/
//		if (max_value > similarityThreshold) {
//			//			System.out.println(max_value + ":"  + max_keys );
//			//			contextManager.println();
//
//			return max_keys;
//		}
//		else {
//			return null;
//		}
//	}
//
//	public List<String> recommendKey(ElementManager contextManager, MicroClusterPairManager clusterPairManager) {
//		double max_value = 0;
//		List<String> max_keys = new ArrayList<String>();
//		//		ElementManager keyManager = new ElementManager();
//
//		// cosine similarity calculator
//		CosineSimilarityCalculator calculator = new CosineSimilarityCalculator();
//
//		for (String keyElement1: clusterPairManager.keySet()) {
//			ElementPairManager microCluster = clusterPairManager.get(keyElement1);
//
//			/*** 해당 부분에서 False 추천이 줄어듬 ***/
//			if (!keyElement1.contains(".java"))
//				continue;			
//
//			//			if (microCluster.getKeyNum() < 2) // size를 올리면 정확도 높아짐...
//			//				continue;
//
//			//			if (contextManager.find(keyElement1) == null)
//			//				continue;
//
//			if (microCluster.getChangeManager().size() <= 2) // size를 올리면 정확도 높아짐...
//				continue;
//			if (microCluster.getVisitManager().size() <= 3) // size를 올리면 정확도 높아짐...
//				continue;
//
//			double value = calculator.CosineSimilarity(contextManager, microCluster.getVisitManager());
//			if (value > similarityThreshold) {
//				//				System.out.println(value);
//
//				// ignore if it is a creator
//				if (keyElement1.indexOf(".java") == -1)
//					continue;
//				if (keyElement1.indexOf(",") == -1)
//					continue;
//
//				if (keyElement1.indexOf(",") + 2 >= keyElement1.indexOf(".java"))
//					continue;
//
//				String filename = keyElement1.substring(keyElement1.indexOf(",") + 2, keyElement1.indexOf(".java"));
//				if ((keyElement1.lastIndexOf(filename)+ filename.length()) == (keyElement1.length()))
//					continue;
//				//					System.out.println("it is a creator function");
//
//				//			if (value > max_value ) { // && (keyElement1.contains(".java"))){ // remove the package level
//				//			if ((value > max_value ) && (keyElement1.contains(".java"))){ // remove the package level
//				if (value > max_value)
//					max_value = value;
//
//				//				keyManager.add(new Element(keyElement1, value));
//				max_keys.add(keyElement1);
//				//						System.out.print("num: " + ++ num + "  ");
//
//			}
//		}
//
//		//		keyManager.sortSimilarity();
//		//		int length = 5;
//		//		if (keyManager.size() < 5)
//		//			length = keyManager.size();
//		//		
//		//		for (int i = 0; i < length; i++) {
//		//			String key = keyManager.elementAt(i).getName();			
//		//			max_keys.add(key);
//		//		}		
//
//		/*** 해당 부분에서 False 추천이 줄어듬 ***/
//		if (max_value > similarityThreshold) {
//			//			System.out.println(max_value + ":"  + max_keys );
//			//			contextManager.println();
//
//			return max_keys;
//		}
//		else {
//			return null;
//		}
//	}
//
////	public void scanfile(XMLEventReader reader) {
////		try {
////
////			XMLEvent event;
////
////			int i = 0, k = 0;
////			currentContextManager = new ElementManager();			
////			while (reader.hasNext()) {
////				if (i < 15) {
////					event = reader.nextEvent();
////					i = countSelectionEdit(event, i); // increase i
////				}
////				else {
////					//					System.out.println(k++);
////					//					contextManager.add(currentContextManager);
////					//					currentContextManager = new ElementManager(); // accumulate by removing this line...					
////					i = 0;
////
////				}
////			}
////			currentContextManager = null;
////
////			//			return contextManager;
////		} catch (Exception e) {
////			e.printStackTrace();
////			//			return null;
////		}
////	}
//
//	int countSelectionEdit(XMLEvent event, int i) {
//
//		StructureHandlePrinter structureHandlePrinter = new StructureHandlePrinter();
//
//		if (event.isStartElement()) {
//			StartElement element = (StartElement) event;
//			//	          System.out.println("Start Element: " + element.getName());
//
//			if (element.getName().toString().equals("InteractionEvent")) {		        	  
//				Attribute attribute1 = element.getAttributeByName(new QName("Kind"));
//				QName name1 = attribute1.getName();
//				String value1 = attribute1.getValue();
//
//				if (value1.equals("selection")) {		        	  
//					Attribute attribute2 = element.getAttributeByName(new QName("StructureHandle"));
//					QName name2 = attribute2.getName();
//					String value2 = attribute2.getValue();
//
//					//		        	  System.out.println("Attribute name/value: " + name2 + "/" + value2);
//					//					if (!value2.contains("http://") && 
//					//						!value2.contains(".jar"))
//					//					out.println("select:" + StructureHandlePrinter.toElement(value2) + " ");
//
//					currentContextManager.insert(structureHandlePrinter.toElement(value2));
//					//					System.out.println(structureHandlePrinter.toElement(value2)); // why "src&lt appeared?"
//					i++;
//					//					vectorManager.insert(StructureHandlePrinter.toElement(value2));	
//					//					tmpSet.addtoPrev(value2);
//					//					freqItems.add(value2);
//				} else if (value1.equals("edit")) {		        	  
//					Attribute attribute2 = element.getAttributeByName(new QName("StructureHandle"));
//					QName name2 = attribute2.getName();
//					String value2 = attribute2.getValue();
//
//					//		        	  System.out.println("Attribute name/value: " + name2 + "/" + value2);
//					//					iRule.addtoPrev(value2);
//					//					currentSet.addtoPrev(value2);
//					//					out.println("edit:" + StructureHandlePrinter.toElement(value2) + " ");
//
//					//					currentManager.insert(StructureHandlePrinter.toElement(value2));
//					//					i++;
//					//					vectorManager.insert(StructureHandlePrinter.toElement(value2));	
//					//					tmpSet.addtoNext(value2);
//					//					freqItems.add(value2);
//				}
//			}		        
//		}
//		if (event.isEndElement()) {
//			EndElement element = (EndElement) event;
//			//	          System.out.println("End element:" + element.getName());
//		}
//		if (event.isCharacters()) {
//			Characters characters = (Characters) event;
//			//	          System.out.println("Text: " + characters.getData());
//		}		
//		return i;
//	}
//
//
//}
