package old.out.recommender;

import java.util.LinkedList;
import java.util.List;

public class ResultList {

	List<ResultFair> iList = new LinkedList<ResultFair>();

	public void add(int iFalse, int iTrue) {
		iList.add(new ResultFair(iFalse, iTrue));
	}

	public void add(ResultFair fair) {
		iList.add(fair);
	}

	public void clear() {
		iList.clear();
	}

	public float calculate1() { // per files...
		float result = 0;

		int iCount = iList.size();
		for (ResultFair fair: iList) {
			float sum = fair.getiFalse() + fair.getiTrue();
			if (sum == 0) {
				iCount--;
			}
			else {
				result = result + fair.getiTrue() / sum;
			}
		}		

		return result / iCount;
	}

	public float calLikelihood() { // per recommendations...
		float result = 0;
		float sum = 0;

		for (ResultFair fair: iList) {		
			sum = sum + fair.getiFalse() + fair.getiTrue();
			result = result + fair.getiTrue();
		}		

		return result / sum;
	}

	public double calPrecision() { // per recommendations...
		double sumOfPrecision = 0;
		double sum = 0;

		for (ResultFair fair: iList) {
			if (fair.getdPrecision() > 0.0) {
				sum = sum + fair.getiFalse() + fair.getiTrue();
				sumOfPrecision = sumOfPrecision + fair.getdPrecision();
			}
		}		

		return sumOfPrecision / sum;
	}

	public double calRecall() { // per recommendations...
		double sumOfRecall = 0;
		double sum = 0;

		for (ResultFair fair: iList) {
			if (fair.getdRecall() > 0.0) {
				sum = sum + fair.getiFalse() + fair.getiTrue();
				sumOfRecall = sumOfRecall + fair.getdRecall();
			}
		}		

		return sumOfRecall / sum;
	}


	public int sum() {
		int sum = 0;

		int iCount = iList.size();
		for (ResultFair fair: iList) {
			sum = sum + fair.getiFalse() + fair.getiTrue();
		}		

		return sum;
	}
	
	public int sumQueries() {
		int sumQueries = 0;

		int iCount = iList.size();
		for (ResultFair fair: iList) {
			sumQueries = sumQueries + fair.getQueries();
		}		

		return sumQueries;
	}

	public float size() {
		return iList.size();
	}

}
