package old.data.elements;


import java.util.Comparator;

import renewed.data.elements.Element;

public class ElementSimilarityComparator implements Comparator<Element> {

	@Override
	public int compare(Element n1, Element n2) {
		return (n1.getSimilarity() > n2.getSimilarity()? 0:1 );
	}

}
