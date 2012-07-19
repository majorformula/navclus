package renewed.test.answer;

import renewed.evaluation.answer.AnswerSheet;
import renewed.evaluation.answer.EditQueue;

public class AnswerTester {

	public static void main(String[] args) {
		
		String directory = "E:/Example";
		
		String file = "Platform-131435-111876.txt";
		
		AnswerSheet answerSheet = new AnswerSheet();
		
		EditQueue editQueue = answerSheet.answer(directory, file);
		editQueue.println();

	}

}
