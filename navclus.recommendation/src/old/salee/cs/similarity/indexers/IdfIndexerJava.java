package old.salee.cs.similarity.indexers;

import org.apache.commons.collections15.Transformer;

import Jama.Matrix;

/**
 * Reduces the weight of words which are commonly found (ie in more documents). The factor by which it is reduced is
 * chosen from the book as: f(m) = 1 + log(N/d(m)) where N = total number of docs in collection d(m) = number of docs
 * containing word m so where a word is more frequent (ie d(m) is high, f(m) would be low.
 * 
 * @author Sujit Pal
 * @version $Revision$
 */
public class IdfIndexerJava implements Transformer<Matrix, Matrix> {

	// added by salee
	public Matrix transform(Matrix matrix) {
		// Phase 1: apply IDF weight to the raw word frequencies
		int n = matrix.getColumnDimension();
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			for (int i = 0; i < matrix.getRowDimension(); i++) {
				double matrixElement = matrix.get(i, j);
				if (matrixElement > 0.0D) {
					double dm = countDocsWithWord(matrix.getMatrix(i, i, 0, matrix.getColumnDimension() - 1));
					matrix.set(i, j, matrix.get(i, j) * (1 + Math.log(n) - Math.log(dm)));
				}
			}
		}
		// Phase 2: normalize the word scores for a single document
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

	private double countDocsWithWord(Matrix rowMatrix) {
		double numDocs = 0.0D;
		for (int j = 0; j < rowMatrix.getColumnDimension(); j++) {
			if (rowMatrix.get(0, j) > 0.0D) {
				numDocs++;
			}
		}
		return numDocs;
	}

}
