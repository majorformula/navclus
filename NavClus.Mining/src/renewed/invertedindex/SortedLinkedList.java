package renewed.invertedindex;

/*
 * SortedLinkedList.java
 *
 * Created on June 27, 2006, 2:47 PM
 *
 */

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Daniel A. Galron
 */
public class SortedLinkedList<E> extends LinkedList<E> {
    
    /** Creates a new instance of SortedLinkedList */
    public SortedLinkedList() {
    }
    
    public SortedLinkedList(SortedLinkedList sl) {
        super(sl);
    }
    
    public void insort(E o) {
//        System.out.println("-----------------------");
        this.insort_aux(o, 0, this.size());
//        System.out.println("-----------------------");
    }
    
    public void merge(SortedLinkedList<E> other) {
        for(Iterator<E> it = other.iterator(); it.hasNext();) {
            E next = it.next();
            this.insort(next);
        }
    }
    
    public int rank(E o) {
        return this.size() - this.indexOf(o);
    }
    
    private void insort_aux(E o, int i, int j) {
        if(i > j) {
            System.err.println("ASSERTION VIOLATION IN SORTEDLINKEDLIST: i > j");
            System.exit(2);
        }
        if((j-i) == 0) {
            //System.out.println("INSERTING AT " + i);
            this.add(i, o);
        } else {
            int pivot = (j - i) / 2;
            //System.out.println("i = " + i + ", j = " + j + ":   pivot = " + pivot);
            if(((Comparable)o).compareTo((Comparable)this.get(i + pivot)) < 0) {
                this.insort_aux(o, i, i + pivot);
            } else {
                this.insort_aux(o, i + pivot + 1, j);
            }
        }
    }
    
    public E removeHighest() {
        return this.removeLast();
    }
    
    public E getHighest() {
        return this.getLast();
    }
    
    
    
    
    public static void main(String[] args) {
        SortedLinkedList<String> list = new SortedLinkedList<String>();
        list.insort("Hello");
        list.insort("there");
        list.insort("world!");
        list.insort("my");
        list.insort("name");
        list.insort("is");
        for(Iterator<String> i = list.iterator(); i.hasNext(); )
            System.out.println(i.next());        
    }
}