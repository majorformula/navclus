package old.data.elements;


import java.util.Comparator;

import renewed.data.elements.Element;

public class RankComparator implements Comparator<Element> {

	@Override
	public int compare(Element n1, Element n2) {
		return (n1.getWeight() > n2.getWeight()? 0:1 );
	}

}
