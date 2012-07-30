package navclus.userinterface.monitor.selections.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jdt.core.IJavaElement;

public class SelectionList {

	private Queue<Selection> selectionList;

	private final int N = 5;

	public SelectionList() {
		this.selectionList = new LinkedList<Selection>();
	}

	public SelectionList(LinkedList<Selection> selectionList) {
		this.selectionList = selectionList;
	}

	public void add(IJavaElement element) {
		if (this.contain(element) == true) {
			return;
		}

		Selection selection = new Selection(element);
		selectionList.add(selection);

		if (selectionList.size() > N) {
			selectionList.remove();
		}
	}

	public void clear() {
		selectionList.clear();
	}

	public boolean contain(IJavaElement element) {
		if (element == null) {
			return false;
		}

		for (Selection selection : selectionList) {
			if (element.equals(selection.getElement())) {
				return true;
			}
		}
		return false;
	}

	public LinkedList<Selection> getSelectionList() {
		return (LinkedList<Selection>) selectionList;
	}

	public boolean IsFull() {
		if (selectionList.size() >= N) {
			return true;
		} else {
			return false;
		}
	}

	public Iterator<Selection> iterator() {
		return selectionList.iterator();
	}

	public void setSelectionList(LinkedList<Selection> selectionList) {
		this.selectionList = selectionList;
	}

	public int size() {
		return selectionList.size();
	}
	
	public void print() {
		for (Selection selection : selectionList) {
			System.out.println("-- selection: " + selection.getElement().getElementName());
		}
	}

}
