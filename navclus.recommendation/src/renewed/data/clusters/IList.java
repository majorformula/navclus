package renewed.data.clusters;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class IList {
	
	LinkedList<Integer> iList = new LinkedList<Integer>();
	
	int bUpdated = 0; // 0 = none, 1 = modified, 2 = inserted
	
	int id = -1;

	public String getUpdated() {
		switch (bUpdated){
		case 0:
			return "nochange";
		case 1:
			return "modified";
		case 2:
			return "inserted";
		}
		return "error";
	}
	
	public int getUpdatedinNum() {
		return bUpdated;
	}

	public void cleared() {
		this.bUpdated = 0;
	}
	
	public void modified() {
		if (bUpdated == 0)
			this.bUpdated = 1;
	}
	
	public void inserted() {
		this.bUpdated = 2;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public LinkedList<Integer> getiList() {
		return iList;
	}

	public void setiList(LinkedList<Integer> iList) {
		this.iList = iList;
	}

	public void add(int index, Integer element) {
		iList.add(index, element);
	}

	public boolean add(Integer e) {
		return iList.add(e);
	}

	public boolean addAll(Collection<? extends Integer> c) {
		return iList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Integer> c) {
		return iList.addAll(index, c);
	}

	public void addFirst(Integer e) {
		iList.addFirst(e);
	}

	public void addLast(Integer e) {
		iList.addLast(e);
	}

	public void clear() {
		iList.clear();
	}

	public Object clone() {
		return iList.clone();
	}

	public boolean contains(Object o) {
		return iList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return iList.containsAll(c);
	}

	public Iterator<Integer> descendingIterator() {
		return iList.descendingIterator();
	}

	public Integer element() {
		return iList.element();
	}

	public boolean equals(Object o) {
		return iList.equals(o);
	}

	public Integer get(int index) {
		return iList.get(index);
	}

	public Integer getFirst() {
		return iList.getFirst();
	}

	public Integer getLast() {
		return iList.getLast();
	}

	public int hashCode() {
		return iList.hashCode();
	}

	public int indexOf(Object o) {
		return iList.indexOf(o);
	}

	public boolean isEmpty() {
		return iList.isEmpty();
	}

	public Iterator<Integer> iterator() {
		return iList.iterator();
	}

	public int lastIndexOf(Object o) {
		return iList.lastIndexOf(o);
	}

	public ListIterator<Integer> listIterator() {
		return iList.listIterator();
	}

	public ListIterator<Integer> listIterator(int index) {
		return iList.listIterator(index);
	}

	public boolean offer(Integer e) {
		return iList.offer(e);
	}

	public boolean offerFirst(Integer e) {
		return iList.offerFirst(e);
	}

	public boolean offerLast(Integer e) {
		return iList.offerLast(e);
	}

	public Integer peek() {
		return iList.peek();
	}

	public Integer peekFirst() {
		return iList.peekFirst();
	}

	public Integer peekLast() {
		return iList.peekLast();
	}

	public Integer poll() {
		return iList.poll();
	}

	public Integer pollFirst() {
		return iList.pollFirst();
	}

	public Integer pollLast() {
		return iList.pollLast();
	}

	public Integer pop() {
		return iList.pop();
	}

	public void push(Integer e) {
		iList.push(e);
	}

	public Integer remove() {
		return iList.remove();
	}

	public Integer remove(int index) {
		return iList.remove(index);
	}

	public boolean remove(Object o) {
		return iList.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return iList.removeAll(c);
	}

	public Integer removeFirst() {
		return iList.removeFirst();
	}

	public boolean removeFirstOccurrence(Object o) {
		return iList.removeFirstOccurrence(o);
	}

	public Integer removeLast() {
		return iList.removeLast();
	}

	public boolean removeLastOccurrence(Object o) {
		return iList.removeLastOccurrence(o);
	}

	public boolean retainAll(Collection<?> c) {
		return iList.retainAll(c);
	}

	public Integer set(int index, Integer element) {
		return iList.set(index, element);
	}

	public int size() {
		return iList.size();
	}

	public List<Integer> subList(int fromIndex, int toIndex) {
		return iList.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return iList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return iList.toArray(a);
	}

	public String toString() {
		return iList.toString();
	}
}