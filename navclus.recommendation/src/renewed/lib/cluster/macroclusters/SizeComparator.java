package renewed.lib.cluster.macroclusters;

import java.util.Comparator;

import renewed.data.elements.ElementManager;

public class SizeComparator implements Comparator<ElementManager> {
	@Override
	public int compare(ElementManager o1, ElementManager o2) {
		if (o1.size() > o2.size() )
			return -1;
		else if (o1.size() > o2.size())
			return 0;
		else
			return 1;
	}
	
}
