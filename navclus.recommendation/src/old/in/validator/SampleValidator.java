package old.in.validator;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;

public class SampleValidator {

	static ElementManager answerManager ;

	public SampleValidator(String testDirectory, String file) {	
		
		// This is an answer sheet!
		answerManager = (new SampleValidatorCounter()).scanfile(testDirectory, file);
	}

	// 해당 부분은 수정해야 함...
	public boolean validate(ElementManager resultManager) {		
		boolean bDecision = false;
		for (int i = 0; i < resultManager.size(); i++) {			
			Element node =  resultManager.elementAt(i);
			if (this.contain(node.getName())) {
				bDecision = true;
			}			
		}		
		return bDecision;
	}

	public boolean contain(String element) {
		if (element == null) return false;

		answerManager.sort();
		for (Element node: answerManager.getVector()) {
			if (element.equals(node.getName()))
				return true;
		}		
		return false;
	}

	public void printChanges() {
		answerManager.println();
	}

}
