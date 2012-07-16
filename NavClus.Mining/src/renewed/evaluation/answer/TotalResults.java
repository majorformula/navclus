package renewed.evaluation.answer;

import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TotalResults {
	
	List<Result> iList = new LinkedList<Result>();
	
	public List<Result> getiList() {
		return iList;
	}

	public void setiList(List<Result> iList) {
		this.iList = iList;
	}

	public boolean add(Result arg0) {
		return iList.add(arg0);
	}
	
	public boolean addAll(Collection<? extends Result> c) {
		return iList.addAll(c);
	}

	public void clear() {
		iList.clear();
	}

	public int size() {
		return iList.size();
	}
	
	public double calculatePrecision() { 
		double precision = 0.0;
		
		for (Result result: iList) {
			precision = precision + result.getPrecision(); 
		}
		
		return precision / iList.size();
	}
	
	public double calculateRecall() { 
		double recall = 0.0;
		
		for (Result result: iList) {
			recall = recall + result.getRecall(); 
		}
		
		return recall / iList.size();
	}
	
	public void printTotalResults(PrintStream outfile) { 
		
		for (Result result: iList) {
			double P = result.getPrecision() ;
			double R = result.getRecall();
			outfile.println("Precision:" + P + "	:	" + "Recall:" + R + "	:	" + "F-score:" + (Double.isNaN(2*P*R/(P+R))? 0.0: 2*P*R/(P+R)) ); 
		}		
	}
	
}