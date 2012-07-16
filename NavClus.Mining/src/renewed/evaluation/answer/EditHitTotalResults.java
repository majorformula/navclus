package renewed.evaluation.answer;

import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EditHitTotalResults {
	
	List<EditHitResult> iList = new LinkedList<EditHitResult>();
	
	public List<EditHitResult> getiList() {
		return iList;
	}

	public void setiList(List<EditHitResult> iList) {
		this.iList = iList;
	}

	public boolean add(EditHitResult arg0) {
		return iList.add(arg0);
	}
	
	public boolean addAll(Collection<? extends EditHitResult> c) {
		return iList.addAll(c);
	}

	public void clear() {
		iList.clear();
	}

	public int size() {
		return iList.size();
	}
	
	public double calculateEditHit() { 
		double editHit = 0.0;
		
		for (EditHitResult editHitResult: iList) {
			if (editHitResult.isToEdit() == true)
				editHit = editHit + 1;
		}
		
		return editHit / iList.size();
	}
	
//	public void printTotalEditHitResults(PrintStream outfile) { 
//		
//		for (EditHitResult EditHitResult: iList) {
//		}		
//	}
	
}