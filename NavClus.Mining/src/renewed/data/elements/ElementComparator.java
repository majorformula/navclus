package renewed.data.elements;


import java.util.Comparator;


public class ElementComparator implements Comparator<Element> {

	@Override
	public int compare(Element n1, Element n2) {
		return (n1.getCount() > n2.getCount()? 0:1 );
	}

}
