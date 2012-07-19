package renewed.evaluation.answer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class EditQueue {
	
	Queue<EditRecord> editQueue;
	
	public EditQueue() {
		this.editQueue = new LinkedList<EditRecord>();
	}
	
	public Queue<EditRecord> getEditQueue() {
		return editQueue;
	}

	public void setEditQueue(Queue<EditRecord> editQueue) {
		this.editQueue = editQueue;
	}
	
	public boolean add(EditRecord e) {
		
		return editQueue.add(e);
	}

	public boolean addAll(Collection<? extends EditRecord> c) {
		return editQueue.addAll(c);
	}

	public void clear() {
		editQueue.clear();
	}

	public boolean contains(Object o) {
		return editQueue.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return editQueue.containsAll(c);
	}

	public EditRecord element() {
		return editQueue.element();
	}

	public boolean equals(Object o) {
		return editQueue.equals(o);
	}

	public int hashCode() {
		return editQueue.hashCode();
	}

	public boolean isEmpty() {
		return editQueue.isEmpty();
	}

	public Iterator<EditRecord> iterator() {
		return editQueue.iterator();
	}

	public boolean offer(EditRecord e) {
		return editQueue.offer(e);
	}

	public EditRecord peek() {
		return editQueue.peek();
	}

	public EditRecord poll() {
		return editQueue.poll();
	}

	public EditRecord remove() {
		return editQueue.remove();
	}

	public boolean remove(Object o) {
		return editQueue.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return editQueue.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return editQueue.retainAll(c);
	}

	public int size() {
		return editQueue.size();
	}

	public Object[] toArray() {
		return editQueue.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return editQueue.toArray(a);
	}
	
	public void println() {
		for (EditRecord editRecord: editQueue) {
			editRecord.println();
		}
	}

}
