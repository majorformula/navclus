package renewed.data.queue;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import renewed.data.elements.Element;

public class EventQueue {


	LinkedList<Event> eventQueue = new LinkedList<Event>();
	int n;
	boolean update = false;

	public EventQueue(int n) {
		this.n = n;
	}
	
	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean add(String element,  String action) {
		return add(new Event(element, action));
	}
	
	public boolean add(Event event) {
		boolean result = eventQueue.add(event);

		if (eventQueue.size() > n) {
			eventQueue.removeFirst();
		}

		return result;
	}
	
	public boolean update(String element,  String action) {
		update = insert(element, action);		
		return update;
	}
	
	public boolean insert(String element,  String action) {
		
		if (!this.contains(element)) {
			return add(new Event(element, action));
		}
		else {
			return false;
		}
	}
	
	public boolean contains(String element) {
		
		for (Event event: eventQueue) {
			if (event.getElement().equals(element))
				return true;
		}
		
		return false;
	}

	public void print() {
		System.out.print("[In Queue] " );
		for (Event event: eventQueue) {
			System.out.print(event.getAction()+ ":" + event.getElement() + ", " );			
		}
		System.out.println();
	}	

	public Set<Event> toSet() {
		Set<Event> eventSet = new LinkedHashSet<Event> ();

		eventSet.addAll(eventQueue);

		return eventSet;
	}
	
	public Set<Element> toStringSet() {
		Set<Element> eventSet = new LinkedHashSet<Element> ();
		
		for (Event event: eventQueue) {
			eventSet.add(new Element(event.getElement()));			
		}

		return eventSet;
	}

	public int size() {
		return eventQueue.size();
	}
	
	public void clear() {
		eventQueue.clear();
	}

}
