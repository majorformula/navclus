package renewed.in.reader;


public class StructureHandlePrinter {

	public String toElement(String sProgramElement) {
		String oProgramElement = sProgramElement;
		
		if (sProgramElement == null) 
			return "*";
		
		if (sProgramElement.contains("http://"))
			return sProgramElement;
		
		if (sProgramElement.contains("src&lt"))
			sProgramElement = sProgramElement.replace("src&lt", "");
		
		if (sProgramElement.contains("/")) 
		sProgramElement = sProgramElement.substring(sProgramElement.lastIndexOf("/"));
		
		int index = sProgramElement.indexOf("~");
		int lastindex = sProgramElement.lastIndexOf("~");
		
		if (index != lastindex) {
			sProgramElement = sProgramElement.substring(0, sProgramElement.indexOf("~", sProgramElement.indexOf("~")+1));
		}
	
//		sProgramElement = sProgramElement.replace(";", ", ");

		sProgramElement = sProgramElement.replace("{", ", ");
		
		sProgramElement = sProgramElement.replace("[", ", ");
		
		sProgramElement = sProgramElement.replace("(", ", ");
		
		sProgramElement = sProgramElement.replace("~", ", ");
		
		sProgramElement = sProgramElement.replace("^", ", ");
		
		sProgramElement = sProgramElement.replace("]", ", ");
		
		sProgramElement = sProgramElement.replace("&lt", "");
		
		sProgramElement = sProgramElement.replace("I", "");
		
		sProgramElement = sProgramElement.replace("/", "");
		
		if (sProgramElement.contains("!")) 
			sProgramElement = sProgramElement.substring(0, sProgramElement.lastIndexOf("!"));

		if (sProgramElement.trim().equals(""))
			System.out.println(oProgramElement);
		
//		System.out.println(sProgramElement);
		return sProgramElement;
		
//		String currentProgramElement = "";

//		/*** special files ***/
//		if (!((sProgramElement.contains("{") || sProgramElement.contains("(")))) { // package ... java and class...
//
//			if ((sProgramElement.contains("/")) && (sProgramElement.contains(";"))) {
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf('/'), sProgramElement.indexOf(';')) + "");				
//				return currentProgramElement;
//			}			
//		}
//
//		if (sProgramElement.contains(";")) {		
//		sProgramElement = sProgramElement.substring(sProgramElement.indexOf(";"));
//	}
//		
//		if (!(sProgramElement.contains("{") || sProgramElement.contains("("))) { // no package
//			return sProgramElement;
//		}
//
//		/*** Package ***/
//		if (sProgramElement.contains("{")) { // package ... java
//			if (sProgramElement.contains(";")) 			
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf(';'), sProgramElement.indexOf('{')) + ", ");
//			else
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(0, sProgramElement.indexOf('{')) + ", ");				
//			//			System.out.print(sProgramElement.substring(sProgramElement.indexOf('<') + 1, sProgramElement.indexOf('{')) + ", ");
//		}		
//		else if (sProgramElement.contains("(")) { // package ... class
//			if (sProgramElement.contains(";")) 			
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf(';'), sProgramElement.indexOf('(')) + ", ");
//			else
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(0, sProgramElement.indexOf('(')) + ", ");				
//			//			System.out.print(sProgramElement.substring(sProgramElement.indexOf('<') + 1, sProgramElement.indexOf('{')) + ", ");
//		}
//
//
//		/*** File ***/
//
//		if (sProgramElement.contains(".java")) { // file {
//			if (sProgramElement.contains("[")) 	
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf('{') + 1, sProgramElement.indexOf("[") ) + ", ");
//			else {
////				System.out.println(sProgramElement);
////				System.out.println(sProgramElement.indexOf('{') + 1);
////				System.out.println(sProgramElement.indexOf(".ja"));
//				currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf('{') + 1, sProgramElement.lastIndexOf(".java") ) + ", ");				
//			////			System.out.print(sProgramElement.substring(sProgramElement.indexOf('{') + 1, sProgramElement.indexOf('[')) + ", ");
//			}
//		}
//		if (sProgramElement.contains(".class")) // file
//			currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf('(') + 1, sProgramElement.indexOf(".class") + 6) + ", ");
//
///*
//		//		
//		if (sProgramElement.contains("[")) { // class
//			String sClass = sProgramElement.substring(sProgramElement.indexOf('[') +1);
//
//			if (sClass.contains("~"))
//				sClass = sClass.substring(0, sClass.indexOf('~'));
//
//			if (sClass.contains("^"))
//				sClass = sClass.substring(0, sClass.indexOf('^'));
//
//			currentProgramElement = currentProgramElement.concat(sClass + ", ");
//			//			System.out.print(sClass + ", ");
//		}
//*/
//		//		
//		if (sProgramElement.contains("~")) { // method
//			String sMethod =  sProgramElement.substring(sProgramElement.indexOf('~') + 1);
//
//			if (sMethod.contains("~"))
//				sMethod = sMethod.substring(0, sMethod.indexOf('~'));
//
//			currentProgramElement = currentProgramElement.concat(sMethod);
//			//			System.out.print(sMethod);
//		}		
//		if (sProgramElement.contains("^")) // field
//			currentProgramElement = currentProgramElement.concat(sProgramElement.substring(sProgramElement.indexOf('^') + 1));
//		//			System.out.print(sProgramElement.substring(sProgramElement.indexOf('^') + 1));
//
//		// cleanup
////		currentProgramElement = currentProgramElement.replaceAll(";", " ");
////		if (currentProgramElement.contains(";"))
//
//		
//		currentProgramElement = currentProgramElement.trim();
//		
//		if (currentProgramElement.contains("&lt"))
//				System.out.println(currentProgramElement);					
//		return currentProgramElement;

	}

}
