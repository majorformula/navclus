package old.test.matrix;

import old.data.matrix.SparseMatrix;

public class SparseMatrixTester {
	
    // test client
    public static void main(String[] args) {
        SparseMatrix A = new SparseMatrix(1001);
//        SparseVector x = new SparseVector(5);
        A.put(Integer.parseInt("1000"), Integer.parseInt("999"), 0.75);
//        A.put(0, 0, 1.0);
//        A.put(1, 1, 1.0);
//        A.put(2, 2, 1.0);
//        A.put(3, 3, 1.0);
//        A.put(4, 4, 1.0);
//        A.put(2, 4, 0.3);
//        x.put(0, 0.75);
//        x.put(2, 0.11);
//        System.out.println("x     : " + x);
        System.out.println("A     : " + A);
//        System.out.println("Ax    : " + A.times(x));
//        System.out.println("A + A : " + A.plus(A));
    }
}
