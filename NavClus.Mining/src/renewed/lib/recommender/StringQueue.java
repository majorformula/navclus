package renewed.lib.recommender;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class StringQueue {

	public LinkedList<String> getStringQueue() {
		return stringQueue;
	}

	public void setStringQueue(LinkedList<String> stringQueue) {
		this.stringQueue = stringQueue;
	}

	LinkedList<String> stringQueue = new LinkedList<String>();
	int n;

	public StringQueue(int n) {
		this.n = n;
	}

	public boolean addAll(Collection<? extends String> c) {
		return stringQueue.addAll(c);
	}
		
	public boolean add(String element) {
		if (stringQueue.contains(element))
			return false;
		
		if (stringQueue.size() >= n) {
			stringQueue.removeFirst();
		}
		return stringQueue.add(element);
	}

	public void removeFirst() {
		stringQueue.removeFirst();
	}	
	
	public void print() {
		System.out.print("[In Queue] " );
		for (String element: stringQueue) {
			System.out.print(element + ", " );			
		}
		System.out.println();
	}	

	public Set<String> toSet() {
		Set<String> stringSet = new LinkedHashSet<String> ();

		stringSet.addAll(stringQueue);

		return stringSet;
	}

	public int size() {
		return stringQueue.size();
	}
	
	public void clear() {
		stringQueue.clear();
	}

}
