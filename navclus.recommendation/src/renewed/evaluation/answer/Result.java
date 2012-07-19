package renewed.evaluation.answer;


public class Result {
	
	private double precision;
	private double recall;
	
	public Result(double precision, double recall) {
		this.precision = precision;
		this.recall = recall;
	}
	
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getRecall() {
		return recall;
	}	
	public void setRecall(double recall) {
		this.recall = recall;
	}
}
