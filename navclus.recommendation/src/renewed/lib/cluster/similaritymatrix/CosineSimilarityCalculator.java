package renewed.lib.cluster.similaritymatrix;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;

public class CosineSimilarityCalculator {
	
	public double CosineSimilarity(ElementManager vectorA, ElementManager vectorB) {
		double AB = 0;
		double SumA = 0;
		double SumB = 0;

		for (int i = 0; i < vectorA.size(); i++) {		
			Element nodeA = vectorA.elementAt(i);			
			
			SumA = SumA +  nodeA.getCount() * nodeA.getCount();
			SumB = 0;
			for (int j = 0; j < vectorB.size(); j++) {
				Element nodeB = vectorB.elementAt(j);					
				SumB = SumB +  nodeB.getCount() * nodeB.getCount();
				if (nodeA.getName().equals(nodeB.getName()))
					AB = AB + nodeA.getCount() * nodeB.getCount();
			}
		}

		double value = 0;
		value = AB / Math.sqrt(SumA * SumB);
		
		return value;
	}
	
	public double AsymmetricSimilarity(ElementManager vectorA, ElementManager vectorB) { // A=context, B=cluster 
		double AB = 0;
		double SumA = 0;
		double SumB = 0;

		for (int i = 0; i < vectorA.size(); i++) {		
			Element nodeA = vectorA.elementAt(i);			
			
			SumA = SumA +  nodeA.getCount() * nodeA.getCount();
			SumB = 0;
			for (int j = 0; j < vectorB.size(); j++) {
				Element nodeB = vectorB.elementAt(j);					
				if (nodeA.getName().equals(nodeB.getName())) {
					AB = AB + nodeA.getCount() * nodeB.getCount();
					SumB = SumB +  nodeB.getCount() * nodeB.getCount();
				}
				else {
					SumB = SumB +  Math.sqrt((double)nodeB.getCount() * nodeB.getCount());					
				}
			}
		}

		double value = 0;
		value = AB / Math.sqrt(SumA * SumB);
		
		return value;
	}
	

}
