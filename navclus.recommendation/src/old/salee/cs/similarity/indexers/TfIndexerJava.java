package old.salee.cs.similarity.indexers;

import org.apache.commons.collections15.Transformer;

import Jama.Matrix;

/**
 * Normalizes the occurence matrix per document. Divides each entry by the sum of occurence values for that column. That
 * way a longer document which has 2 occurences of a certain word will not be ranked higher than a shorter document with
 * 1 occurence of the word for that word. At the end of this transformation, the values are the frequency of the word in
 * the document.
 * 
 * @author Sujit Pal
 * @version $Revision$
 */
public class TfIndexerJava implements Transformer<Matrix, Matrix> {

	// added by salee
	public Matrix transform(Matrix matrix) {
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			double sum = sum(matrix.getMatrix(0, matrix.getRowDimension() - 1, j, j));
			for (int i = 0; i < matrix.getRowDimension(); i++) {
				matrix.set(i, j, (matrix.get(i, j) / sum));
			}
		}
		return matrix;
	}

	// added by salee
	private double sum(Matrix colMatrix) {
		double sum = 0.0D;
		for (int i = 0; i < colMatrix.getRowDimension(); i++) {
			sum += colMatrix.get(i, 0);
		}
		return sum;
	}
}
