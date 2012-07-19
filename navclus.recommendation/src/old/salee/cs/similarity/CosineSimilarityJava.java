package old.salee.cs.similarity;

/**
 * Implements Cosine Similarity for a term document matrix.
 * A o B = x1*x2 + y1*y2
 * dist(A,0) = sqrt((xa-x0)^2 + (ya-y0)^2) == |A|
 * Therefore:
 * sim(A,B) = cos t = A o B/|A|x|B|  
 * 
 * @author Sujit Pal
 * @version $Revision$
 */
//Source: src/main/java/src/com/mycompany/myapp/similarity/CosineSimilarity.java
import Jama.Matrix;

public class CosineSimilarityJava extends AbstractSimilarityJava {

	@Override
	protected double computeSimilarity(Matrix sourceDoc, Matrix targetDoc) {
		double dotProduct = sourceDoc.arrayTimes(targetDoc).norm1();
		double eucledianDist = sourceDoc.normF() * targetDoc.normF();
		return dotProduct / eucledianDist;
	}
}