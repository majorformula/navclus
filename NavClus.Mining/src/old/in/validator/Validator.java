package old.in.validator;

import java.util.HashSet;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;


public class Validator {

	ElementManager validateManager ;
	double dPrecision, dRecall;

	// This is an answer sheet!
	public Validator(String testDirectory, String file, HashSet<String> elementSet) {	
		validateManager = new ElementManager();
		
		ElementManager elementManager = (new ValidatorCounter()).scanfile(testDirectory, file);		
		for (Element node: elementManager.getVector()) {
			if (elementSet.contains(node.getName())) {
				validateManager.add(node);
			}
		}		
	}
	
	public Validator(String testDirectory, String file) {	
		validateManager = (new ValidatorCounter()).scanfile(testDirectory, file);
	}

	// check precision, recall, likeliness...
	public boolean validate(ElementManager recommendManager) {		
		boolean bDecision = false;
		
//		// precision: 10 of recommendManager - intersection - validateManager / 10 of recommendManager
//		double SumR = recommendManager.size();
//		double SumV = validateManager.size();
//		double intersectionRV = 0;
//				
//		for (int j = 0; j < recommendManager.size(); j++) {
//			Element node =  recommendManager.elementAt(j);
//			if (this.contain(recommendManager.elementAt(j).getName())) {
//				intersectionRV++;
//			}
//		}
//		
//		// Precision & Recall
//		if (SumR == 0)
//			dPrecision = 0;
//		else			
//			dPrecision = intersectionRV / SumR;
//		
//		if (SumV == 0)
//			dRecall = 0;
//		else
//			dRecall = intersectionRV / SumV;
//		
//		System.out.println("Precision:" + dPrecision + ", " 
//				         + "Recall:" + dRecall);		
//		
//		// recall: recommendManager - intersection - validateManager / validateManager

		// Likelihood
		int Length = 3 /*resultManager.size()*/;
		if (recommendManager.size() < 3)
			Length = recommendManager.size();
		
		for (int i = 0; i < Length ; i++) {
			Element node =  recommendManager.elementAt(i);
			if (this.contain(recommendManager.elementAt(i).getName())) {
				bDecision = true;
			}
		}
		
		return bDecision;
	}

	// precision: 10 of recommendManager - intersection - validateManager / 10 of recommendManager
	public double calPrecision(ElementManager recommendManager) {

		double SumR = recommendManager.size();
		SumR = (SumR < 10)? SumR : 10;

		double intersectionRV = 0;
				
		for (int j = 0; j < SumR; j++) {
			Element node =  recommendManager.elementAt(j);
			if (this.contain(recommendManager.elementAt(j).getName())) {
				intersectionRV++;
			}
		}
		
		// Precision 
		if (SumR == 0)
			dPrecision = 0;
		else			
			dPrecision = intersectionRV / SumR;
			
//		System.out.println("Precision:" + dPrecision + ", " 
//				         + "Recall:" + dRecall);		
		
		// recall: recommendManager - intersection - validateManager / validateManager
		
		
		return dPrecision;
	}

	// getRecall
	public double calRecall(ElementManager recommendManager) {
		double SumR = recommendManager.size();
		SumR = (SumR < 10)? SumR : 10;
		
		// precision: 10 of recommendManager - intersection - validateManager / 10 of recommendManager
		double SumV = validateManager.size();
		double intersectionRV = 0;
				
		for (int j = 0; j < SumR; j++) {
			Element node =  recommendManager.elementAt(j);
			if (this.contain(recommendManager.elementAt(j).getName())) {
				intersectionRV++;
			}
		}
		
		// Recall	
		if (SumV == 0)
			dRecall = 0;
		else
			dRecall = intersectionRV / SumV;
		
//		System.out.println("Precision:" + dPrecision + ", " 
//				         + "Recall:" + dRecall);		
		
		// recall: recommendManager - intersection - validateManager / validateManager
		return dRecall;
	}
	
	
	public boolean contain(String element) {
		if (element == null) return false;

		validateManager.sort();

		for (Element node: validateManager.getVector()) {
			if (element.equals(node.getName()))
				return true;
		}		
		return false;		
	}

	public void printChanges() {
		validateManager.println();
	}
	
	public int size() {
		return validateManager.size();
	}


}
