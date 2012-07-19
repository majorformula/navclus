package renewed.data.clusters;

public class IJPair {
	
	int i;
	int j;
	
	public IJPair(int i, int j) {
		this.i = i;
		this.j = j;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	
	public void set(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public void print() {
		System.out.println("i = " + i + ", " + "j = " + j);		
	}
	
	public void convert() {
		int tmp;
		tmp = this.i;
		this.i = this.j;
		this.j = tmp;
	}

}
