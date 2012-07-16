package renewed.data.clusters;

import java.util.Iterator;
import java.util.LinkedList;




public class MatrixPairManager {
	
	LinkedList<IJPair> Pairs = new LinkedList<IJPair>();

	public Iterator<IJPair> iterator() {
		return Pairs.iterator();
	}

	public LinkedList<IJPair> getPairs() {
		return Pairs;
	}

	public void setPairs(LinkedList<IJPair> pairs) {
		Pairs = pairs;
	}

	
	public boolean add(IJPair e) {
		if (e.getI() > e.getJ())
			e.convert();
		
		if (this.contains(e))
			return false;
		
		return Pairs.add(e);
	}
	
	public void println() {
		Iterator iterator = Pairs.iterator();
		
		while (iterator.hasNext()) {
			IJPair pair = (IJPair) iterator.next();
			pair.print();			
		}
	}

	public boolean contains(IJPair e) {
		
		for (IJPair pair: Pairs) {
			if ((pair.getI() == e.getI()) && (pair.getJ() == e.getJ())) {
				return true;
			}
		}
		
		return false;
	}
	
	public int size() {
		return Pairs.size();
	}

}
