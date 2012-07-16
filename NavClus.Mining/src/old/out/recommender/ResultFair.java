package old.out.recommender;

public class ResultFair {


	public int getQueries() {
		return iQueries;
	}

	public void setQueries(int iQueries) {
		this.iQueries = iQueries;
	}

	int iFalse;
	int iTrue;
	double sumOfPrecision;
	double sumOfRecall;
	
	int iQueries = 0;

	public ResultFair(int iFalse, int iTrue) {
		this.iFalse = iFalse;
		this.iTrue = iTrue;
	}
	
	public ResultFair(int iFalse, int iTrue, double dPrecision, double dRecall) {
		this.iFalse = iFalse;
		this.iTrue = iTrue;
		this.sumOfPrecision = dPrecision;
		this.sumOfRecall = dRecall;
	}
	
	public ResultFair(int iFalse, int iTrue, double dPrecision, double dRecall, int iQueries) {
		this.iFalse = iFalse;
		this.iTrue = iTrue;
		this.sumOfPrecision = dPrecision;
		this.sumOfRecall = dRecall;
		this.iQueries = iQueries;
	}
	
	
	public int getiFalse() {
		return iFalse;
	}
	public void setiFalse(int iFalse) {
		this.iFalse = iFalse;
	}
	
	public int getiTrue() {
		return iTrue;
	}
	public void setiTrue(int iTrue) {
		this.iTrue = iTrue;
	}

	public double getdPrecision() {
		return sumOfPrecision;
	}

	public void setdPrecision(double dPrecision) {
		this.sumOfPrecision = dPrecision;
	}

	public double getdRecall() {
		return sumOfRecall;
	}

	public void setdRecall(double dRecall) {
		this.sumOfRecall = dRecall;
	}
	
}
