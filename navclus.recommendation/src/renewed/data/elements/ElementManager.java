package renewed.data.elements;


import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import old.data.elements.ElementSimilarityComparator;
import old.data.elements.RankComparator;

public class ElementManager {

	Vector<Element> vector = new Vector<Element>();	
	int bUpdated = 0; // 0 = none, 1 = modified, 2 = inserted
	
	Integer parentKey = -1;	
	Integer childrenKeys = 0; 
	
	public void addChild() {
		childrenKeys++;
	}
	
	public void removeChild() {
		childrenKeys--;
	}
	
	public Integer getChildren() {
		return childrenKeys;
	}

	public int getUpdated() {
		return bUpdated;
	}

	public void setUpdated(int bUpdated) {
		this.bUpdated = bUpdated;
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
	
	public Integer getParentKey() {
		return parentKey;
	}

	public void setParentKey(Integer parentKey) {
		this.parentKey = parentKey;
	}

	/*** Vector ***/
	public Vector<Element> getVector() {
		return vector;
	}

	public void setVector(Vector<Element> vector) {
		this.vector = vector;
	}

	public HashSet<String> getElementSet() {
		HashSet<String> elements = new HashSet<String>();
		
		for (Element element: vector) {
			elements.add(element.getName());
		}
		
		return elements;
	}
	
	public int getCount(String element_name) {
		if (element_name == null)
			return 0;
			
		for (Element element: vector) {
			if (element_name.equals(element.getName())) {
				return element.getCount();
			}
		}		
		return 0;
	}	
	
	public boolean add(Element arg0) {
		return vector.add(arg0);
	}

	public void add(String first) {
		vector.add(new Element(first));
	}

	public void add(String first, int count) {
		vector.add(new Element(first, count));
	}

	public void clear() {
		vector.clear();
	}

	public Element elementAt(int index) {
		return vector.elementAt(index);
	}


	public Element find(String first) {
		if (first == null) return null;

		Iterator iterator = vector.iterator();

		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();

			if (first.equals(node.getName())) 
				return node;
		}

		return null;
	}

	public void words_insert(String elementName) {
		if (elementName == null) return;		
		//		String[] words = elementName.split(", "); //s
		////		String[] words = elementName.split("\\s");
		//		for (int i = 0; i < words.length; i++) {
		//			if (words[i].length() <=3)
		//				return;
		//			if (words[i].contains("&"))
		//				return;
		//			
		//			if (i == (words.length -1)) // the last word
		//				this.insert(words[i], 10); // weighing in previous
		//			else	
		//				this.insert(words[i]);	
		//		}

		this.insert(elementName);
	}

	public void insert(String elementName, int count) {
		if (elementName == null) return;

		Element node = this.find(elementName);

		if (node == null)
			this.add(elementName, count);
		else
			node.setCount(node.getCount() + count);
	}
	
	public void insert(String elementName, int count, boolean bEdit) {
		if (elementName == null) return;

		Element element = this.find(elementName);

		if (element == null)
			this.add(elementName, count, bEdit);
		else {
			element.setCount(element.getCount() + count);
			element.setEdit(bEdit);
		}
	}
	
	public void add(String first, int count, boolean bEdit) {
		vector.add(new Element(first, count, bEdit));
	}

	public void insert(String elementName) {
		if (elementName == null) return;

		Element node = this.find(elementName);

		if (node == null)
			this.add(elementName);
		else
			node.increase();
	}

	public void increase(String elementName) {
		if (elementName == null) return;

		Element node = this.find(elementName);
		if (node == null)
			return;

		node.increase();
	}


	public Iterator<Element> iterator() {
		return vector.iterator();
	}

	public void merge(ElementManager nodeManager, String key) {
		if (nodeManager == null) return;

		Iterator otherIterator = nodeManager.iterator();

		while (otherIterator.hasNext()) {
			Element otherNode = (Element) otherIterator.next();
			String otherNodeName = otherNode.getName();			
			if (key.equals(otherNodeName)) continue;

			Element thisNode = this.find(otherNodeName);
			if (thisNode == null) {
				this.add(new Element(otherNodeName, otherNode.getCount(), otherNode.isEdit()));
			} else {
				thisNode.setCount(thisNode.getCount() + otherNode.getCount());
				if (otherNode.isEdit()) {
					thisNode.setEdit(true);
				}
			}
		}	
	}
	
	public void merge(ElementManager nodeManager) {
		if (nodeManager == null) return;

		Iterator otherIterator = nodeManager.iterator();

		while (otherIterator.hasNext()) {
			Element otherNode = (Element) otherIterator.next();
			String otherNodeName = otherNode.getName();			

			Element thisNode = this.find(otherNodeName);
			if (thisNode == null) {
				this.add(new Element(otherNodeName, otherNode.getCount(), otherNode.isEdit()));
			} else {
				thisNode.setCount(thisNode.getCount() + otherNode.getCount());
				if (otherNode.isEdit()) {
					thisNode.setEdit(true);
				}
			}
		}	
	}

	public void println(PrintStream outfile) {
		Iterator iterator = vector.iterator();

		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			if (node.getCount() > 0)
				node.println(outfile);
			else
				break;
		}
	}

	public void println() {
		Iterator iterator = vector.iterator();

		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			if (node.getCount() > 0)
				node.println();
			else
				break;
		}
	}

	public void println(int iThreshold) {
		Iterator iterator = vector.iterator();

		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			if (node.getCount() >= iThreshold)
				node.println();
//			else
//				break;
		}
	}
	
	public void println(int iThreshold, int iCount) {
		Iterator iterator = vector.iterator();

		int cnt = 0;
		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			if (node.getCount() >= iThreshold) {
				node.println();
				cnt++;
			}
//			else
//				break;
			
			if (cnt >= iCount)
				break;
		}
	}
	
	
	
	
//	public void println(int iThreshold, int iCount) {
//		Iterator iterator = vector.iterator();
//
//		int cnt = 0;
//		while (iterator.hasNext()) {
//			Element element = (Element) iterator.next();
//			if (element.getCount() >= iThreshold) {
//				if (element.isEdit()) {
//					element.println();
//					cnt++;
//				}
//			}
////			else
////				break;
//			if (cnt >= iCount)
//				break;
//		}
//	}

	public int size() {
		return vector.size();
	}

	public void sortSimilarity() {
		ElementSimilarityComparator paircomparator = new ElementSimilarityComparator();
		Collections.sort(vector, paircomparator);
	}
	
	public void sort() {
		ElementComparator paircomparator = new ElementComparator();
		Collections.sort(vector, paircomparator);
	}

	public void rank() {
		RankComparator paircomparator = new RankComparator();
		Collections.sort(vector, paircomparator);
	}
	
	public Set<String> getRecommendationSet(int iThreshold, int iCount) {

		Set<String> recommendationSet = new TreeSet<String>();

		Iterator iterator = vector.iterator();
		int cnt = 0;
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
//			System.out.println(element.getName());
			
			if (element.getCount() >= iThreshold) {
//				if (element.isEdit()) {
					recommendationSet.add(element.getName());
					//				node.println();
					cnt++;
//				}
			}
			else
				break;
			if (cnt >= iCount)
				break;
		}

		return recommendationSet;
	}

/////////////////////////////////////////////////////////////////////////////////////////////	
//	// for a change manager
//	public void weight(ElementManager contextManager, ElementManager visitManager) {
//		Iterator iterator = vector.iterator();
//		while (iterator.hasNext()) {
//			Element node = (Element) iterator.next();
//
//			// weight with context
//			String name = node.getName();
//			int packageValue = 0;
//			if (name.contains(";") && name.contains(",")) {
//				String packageName = name.substring(name.indexOf(';'), name.indexOf(','));
//				//				System.out.println("package: " + packageName);
//				packageValue = contextManager.getValue(packageName);
//				//				node.setWeight(node.getCount() * packageValue);
//			}
//			if (packageValue == 0)
//				packageValue = 1;
//
//			// weight with visitManager
//			if (name.contains(".java,")) {
//				String fileName = name.substring(0, name.indexOf(".java,") + 5);
//				int fileValue = visitManager.getValue(fileName);
//
//				if (fileValue == 0)
//					fileValue = 1;
//
//				node.setWeight(node.getCount() * (packageValue ) * (  (double) fileValue) );				
//			}
//		}		
//	}
//
//	/*** HashMap ***/
//	public void copyPackage2HashMap() {		
//		Iterator iterator = vector.iterator();
//
//		while (iterator.hasNext()) {
//			Element node = (Element) iterator.next();
//			String elementName = node.getName();
//
//			String[] words = elementName.split(", "); //s
//			for (int i = 0; i < words.length; i++) {
//				if (words[i].length() <=3)
//					continue;
//				if (words[i].contains("&"))
//					continue;
//
//				if (words[i].contains(";"))
//					hashmap.put(node.getName(), node.getCount());
//			}
//		}		
//	}
//
//	public void copyFile2HashMap() {		
//		Iterator iterator = vector.iterator();
//
//		while (iterator.hasNext()) {
//			Element node = (Element) iterator.next();
//			String elementName = node.getName();
//
//			String[] words = elementName.split(", "); //s
//			for (int i = 0; i < words.length; i++) {
//				if (words[i].length() <=3)
//					continue;
//				if (words[i].contains("&"))
//					continue;
//
//				if (node.getName().contains(".java")) {
//					//				node.println();
//					hashmap.put(node.getName(), node.getCount());
//				}
//			}
//		}		
//	}
//
//	public void printHashMap() {
//		Set<String> keys = hashmap.keySet();
//
//		for (String key: keys) {
//			int value = hashmap.get(key);
//			System.out.println(value + ", " + key /* firstElement.getElementName() */);
//		}
//	}
//
//	public int getValue(String key) {
//		return (hashmap.get(key) == null? 0: hashmap.get(key));
//	}
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void mergewithDoc(ElementManager nodeManager) {
		Iterator otherIterator = nodeManager.iterator();

		while (otherIterator.hasNext()) {
			Element otherNode = (Element) otherIterator.next();
			String otherNodeName = otherNode.getName();

			Element thisNode = this.find(otherNodeName);

			if (thisNode == null) {
				this.add(new Element(otherNodeName, otherNode.getCount()));
			} else {
				thisNode.setCount(thisNode.getCount() + otherNode.getCount());
				thisNode.setDoc(thisNode.getDoc() + otherNode.getDoc());				
			}
		}	
	}

	public void mergewithXDoc(ElementManager nodeManager) {
		Iterator otherIterator = nodeManager.iterator();

		while (otherIterator.hasNext()) {
			Element otherNode = (Element) otherIterator.next();
			String otherNodeName = otherNode.getName();

			Element thisNode = this.find(otherNodeName);

			if (thisNode == null) {
				Element newNode = new Element(otherNodeName, otherNode.getCount());
				newNode.setX(otherNode.getCount());
				newNode.setXTFIDF(otherNode.getTFIDF());
				this.add(newNode);
			} else {
				thisNode.setCount(thisNode.getCount() + otherNode.getCount());
				thisNode.setDoc(thisNode.getDoc() + otherNode.getDoc());
				thisNode.setX(otherNode.getCount());
				thisNode.setXTFIDF(otherNode.getTFIDF());
			}
		}	
	}

	public void mergewithYDoc(ElementManager nodeManager) {
		Iterator otherIterator = nodeManager.iterator();

		while (otherIterator.hasNext()) {
			Element otherNode = (Element) otherIterator.next();
			String otherNodeName = otherNode.getName();

			Element thisNode = this.find(otherNodeName);

			if (thisNode == null) {
				Element newNode = new Element(otherNodeName, otherNode.getCount());
				newNode.setY(otherNode.getCount());
				newNode.setYTFIDF(otherNode.getTFIDF());
				this.add(newNode);
			} else {
				thisNode.setCount(thisNode.getCount() + otherNode.getCount());
				thisNode.setDoc(thisNode.getDoc() + otherNode.getDoc());
				thisNode.setY(otherNode.getCount());
				thisNode.setYTFIDF(otherNode.getTFIDF());
			}
		}	
	}

	public void idf(int Total) {
		Iterator iterator = vector.iterator();

		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();

			node.idf(Total);
			//			System.out.println(node.idf(Total));
		}
	}

	public void tf() {
		Iterator iterator = vector.iterator();

		double sum = 0;
		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			sum = sum + node.getCount();
		}

		iterator = vector.iterator();
		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			node.tf(sum);
			//			System.out.println(node.tf(sum));
		}

	}


	public void tfidf(ElementManager termManager, int Total) {

		// calculate	
		Iterator iterator = vector.iterator();		
		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();

			// ...but we need idf per this term...
			String elementName = node.getName();
			Element idfnode = termManager.find(elementName);

			if (idfnode == null)
				node.setIDF(Math.log(((double) Total) /  ((double) 1)));		
			else
				node.setIDF(idfnode.getIDF());			

			node.tfidf();
			// ...we also have to do tf per this term...
			//			System.out.println(node.tfidf());
			//			node.println();
		}
	}

	public int countFiles() {		
		Iterator iterator = vector.iterator();

		int count = 0;
		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();

			if (node.getName().trim().endsWith(".java")) 
				count++;
			else if (node.getName().trim().endsWith(".class")) 
				count++;
		}

		return count;
	}
	
	public void add(int index, Element element) {
		vector.add(index, element);
	}

	public boolean addAll(Collection<? extends Element> c) {
		return vector.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Element> c) {
		return vector.addAll(index, c);
	}

	public void addElement(Element obj) {
		vector.addElement(obj);
	}

	public int capacity() {
		return vector.capacity();
	}

	public Object clone() {
		return vector.clone();
	}

	public boolean contains(Object o) {
		return vector.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return vector.containsAll(c);
	}

	public void copyInto(Object[] anArray) {
		vector.copyInto(anArray);
	}

	public Enumeration<Element> elements() {
		return vector.elements();
	}

	public void ensureCapacity(int minCapacity) {
		vector.ensureCapacity(minCapacity);
	}

	public boolean equals(Object o) {
		return vector.equals(o);
	}

	public Element firstElement() {
		return vector.firstElement();
	}

	public Element get(int index) {
		return vector.get(index);
	}

	public int hashCode() {
		return vector.hashCode();
	}

	public int indexOf(Object o, int index) {
		return vector.indexOf(o, index);
	}

	public int indexOf(Object o) {
		return vector.indexOf(o);
	}

	public void insertElementAt(Element obj, int index) {
		vector.insertElementAt(obj, index);
	}

	public boolean isEmpty() {
		return vector.isEmpty();
	}

	public Element lastElement() {
		return vector.lastElement();
	}

	public int lastIndexOf(Object o, int index) {
		return vector.lastIndexOf(o, index);
	}

	public int lastIndexOf(Object o) {
		return vector.lastIndexOf(o);
	}

	public ListIterator<Element> listIterator() {
		return vector.listIterator();
	}

	public ListIterator<Element> listIterator(int arg0) {
		return vector.listIterator(arg0);
	}

	public Element remove(int index) {
		return vector.remove(index);
	}

	public boolean remove(Object o) {
		return vector.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return vector.removeAll(c);
	}

	public void removeAllElements() {
		vector.removeAllElements();
	}

	public boolean removeElement(Object obj) {
		return vector.removeElement(obj);
	}

	public void removeElementAt(int index) {
		vector.removeElementAt(index);
	}

	public boolean retainAll(Collection<?> c) {
		return vector.retainAll(c);
	}

	public Element set(int index, Element element) {
		return vector.set(index, element);
	}

	public void setElementAt(Element obj, int index) {
		vector.setElementAt(obj, index);
	}

	public void setSize(int newSize) {
		vector.setSize(newSize);
	}

	public List<Element> subList(int fromIndex, int toIndex) {
		return vector.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return vector.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return vector.toArray(a);
	}

	public String toString() {
		return vector.toString();
	}

	public void trimToSize() {
		vector.trimToSize();
	}
}