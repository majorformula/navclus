package old.data.matrix;


public class MatrixManager {
	
	SparseMatrix matrix; 

	public MatrixManager(int i) {
		matrix = new SparseMatrix(i);		
	}
	
	public void setMatrix(int i, int j, double value) {
		matrix.put(i, j, value);
	}
	
	public SparseMatrix getMatrix() {
		return matrix;
	}	
	
	public void print() {
        	matrix.println();
	}
	
    public int getLength() {
		return matrix.getLength();
	}
    
    public SparseVector getRows(int i) {
		return  matrix.getRows(i);
	}
}
