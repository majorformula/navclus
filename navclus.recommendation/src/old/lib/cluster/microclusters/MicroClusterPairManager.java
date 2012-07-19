package old.lib.cluster.microclusters;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import old.data.pairs.ElementPairManager;
import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;



public class MicroClusterPairManager {
	
	HashSet<String> hashset = new HashSet<String>();
	
	public HashSet<String> getSet() {
		return hashset;
	}

	public void setSet(HashSet<String> hashset) {
		this.hashset = hashset;
	}

	public boolean addSet(String arg0) {
		return hashset.add(arg0);
	}

	public boolean addAllSet(Collection<? extends String> arg0) {
		return hashset.addAll(arg0);
	}

	public boolean containsSet(Object arg0) {
		return hashset.contains(arg0);
	}

	public boolean containsAllSet(Collection<?> arg0) {
		return hashset.containsAll(arg0);
	}

	public Iterator<String> iteratorSet() {
		return hashset.iterator();
	}

	public boolean removeAllSet(Collection<?> arg0) {
		return hashset.removeAll(arg0);
	}

	public boolean retainAllSet(Collection<?> arg0) {
		return hashset.retainAll(arg0);
	}

	public Object[] toArraySet() {
		return hashset.toArray();
	}

	public <T> T[] toArraySet(T[] arg0) {
		return hashset.toArray(arg0);
	}

	HashMap<String, ElementPairManager> hashmap = new HashMap<String, ElementPairManager> (); // key string...data list of nodes...(okay, let's use NodeManager...)

	public void clear() {
		hashmap.clear();
	}

	public Object clone() {
		return hashmap.clone();
	}

	public boolean containsKey(Object arg0) {
		return hashmap.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) {
		return hashmap.containsValue(arg0);
	}

	public Set<Entry<String, ElementPairManager>> entrySet() {
		return hashmap.entrySet();
	}

	public boolean equals(Object arg0) {
		return hashmap.equals(arg0);
	}

	public ElementPairManager get(Object arg0) {
		return hashmap.get(arg0);
	}

	public int hashCode() {
		return hashmap.hashCode();
	}

	public boolean isEmpty() {
		return hashmap.isEmpty();
	}

	public Set<String> keySet() {
		return hashmap.keySet();
	}

	public ElementPairManager put(String arg0, ElementPairManager arg1) {
		return hashmap.put(arg0, arg1);
	}

	public void putAll(Map<? extends String, ? extends ElementPairManager> arg0) {
		hashmap.putAll(arg0);
	}

	public ElementPairManager remove(Object arg0) {
		return hashmap.remove(arg0);
	}

	public int size() {
		return hashmap.size();
	}

	public String toString() {
		return hashmap.toString();
	}

	public Collection<ElementPairManager> values() {
		return hashmap.values();
	}

	SegmentPair segmentPair = new SegmentPair();

	public void insert2visit(String elementName) {
		if (elementName == null) return;
		if (elementName == "done") {
			// segment
			segmentPair.segment(); // 기존 걸 저장하고 새로운 데이타 저장소 만든다.
			return;
		}

		segmentPair.setupKeyName(elementName); // only for the initial state

		if (segmentPair.revisited(elementName)) {
//			System.out.println("revisit:" + elementName );
			
			segmentPair.segment();
			segmentPair.setupKeyName(elementName); // new setting for the key name			
		}

		// visit add...
		segmentPair.visit_add(elementName);
	}	


	public void insert2change(String elementName) {
		if (elementName == null) return;
		if (elementName == "done") {
			// segment
			segmentPair.segment();
			return;
		}

		segmentPair.setupKeyName(elementName); // only for the initial state		

		if (segmentPair.rechanged(elementName)) {
			segmentPair.segment();
			segmentPair.setupKeyName(elementName); // new setting for the key name			
		}

		// change add...
		segmentPair.change_add(elementName);
	}

	class SegmentPair {

		// for insert2segment
		ElementPairManager pairManager = new ElementPairManager(); // for insert2segment
		String keyName = null;
		String previousName = null ; // for insert2segment
		String previousAction = "" ; // for insert2segment

		void setupKeyName(String elementName) { // 해당 엘리먼트를 저장해야 한다.
			if ((previousName == null) /*&& (elementName.contains("*.java")) */) { 
				keyName = elementName; // it is the initial state
//				pairManager.increaseKeyNum(); // 어디서 해당 번호를 사용하는지 모르겠네....// 일관성 맞춤
			}
		}

		void visit_add(String elementName) {
			// for a visited element
			pairManager.getVisitManager().insert(elementName);		
			visit_addFiles(elementName);
			
			previousName = elementName;
			previousAction = "visit";
		}

		void visit_addFiles(String elementName) {
			if (elementName.length() <=3)
				return;
			if (elementName.contains("&"))				
				return;
			
			if (elementName.contains(".java,"))
				elementName = elementName.substring(0, elementName.indexOf(".java,") + 5);			
			
			pairManager.getVisitFileManager().insert(elementName);	 // 일관성 맞춤
		}
		
		void change_add(String elementName) {
			if (elementName.length() <=3)
				return;
			if (elementName.contains("&"))
				return;
			
			pairManager.getChangeManager().insert(elementName);	
			previousName = elementName;
			previousAction = "change";
		}

		private boolean revisited(String elementName) {
			if (elementName.equals(previousName) && previousAction.equals("visit")) { // if it is continuous...insert the element...
				return false;
			}

			Element currentNode = pairManager.getVisitManager().find(elementName); // if it is new...insert the element...
			if (currentNode == null)  { // else --> add
				return false;
			}
			else { 
				return true;
			}
		}

		private boolean rechanged(String elementName) {
			if (elementName.equals(previousName) && previousAction.equals("change")) { // if it is continuous...insert the element...
				return false;
			}

			Element currentNode = pairManager.getChangeManager().find(elementName); // if it is new...insert the element...
			if (currentNode == null)  { // else --> add
				return false;
			}
			else { 
				return true;
			}
		}

		public void segment() {

			// 현재 키가 이전 키와 중복될 때 노드와서 합치기.
			if (containsKey(keyName)) { // if a key --> merge
				ElementPairManager storedPairManager = get(keyName);
				storedPairManager.merge(pairManager);
				
				put(keyName, storedPairManager); //*** put here! 					
			}
			else	{ // if no key --> insert
				if (keyName != null)
					put(keyName, pairManager); //*** put here!
			}		

			pairManager = new ElementPairManager();			
			segment_clear();
		}

		public void segment_clear() {
			pairManager.clear() ; // for insert2segment ... 2 clear
			keyName = null;
			previousName = null ; // for insert2segment
			previousAction = "" ; // for insert2segment		
		}
	}		

	public void printPair() {
		Set<String> keyStrings = this.keySet();

		for (String keyString: keyStrings) {
			//		System.out.println("key:" + keyString);
			ElementPairManager microPairManager = this.get(keyString);
			System.out.print(microPairManager.getVisitManager().size() + " ");
			System.out.println(microPairManager.getChangeManager().size());
		}
	}

	public void printVisitContext() {
		Set<String> keyStrings = this.keySet();

		for (String keyString: keyStrings) {
			System.out.println("key:" + keyString);
			ElementPairManager nodeManager = this.get(keyString);
			nodeManager.getVisitManager().println();
		}
	}
	
	public void printKeys() {		
		Set<String> keyStrings = this.keySet();

		System.out.println("*** Keys ***");
		for (String keyString: keyStrings) {
			ElementPairManager elementPairManager = this.get(keyString);			
			Element element = elementPairManager.getVisitManager().find(keyString);

			if (element != null)
				System.out.println(" : " + element.getCount() +  " : " + element.getName() );
		}
	}
	
	public void println(int iThreshold) {		
		Set<String> keyStrings = this.keySet();

		System.out.println("*** Clusters ***");
		for (String keyString: keyStrings) {
			System.out.println("Key:" + keyString);
			ElementPairManager elementPairManager = this.get(keyString);
			
			System.out.println("Visit:");
			elementPairManager.getVisitManager().println(iThreshold);
			
			System.out.println("Change:");
			elementPairManager.getChangeManager().println();			
		}
	}
	
	public void ignore(int iThreshold) {		
		Set<String> keyStrings = this.keySet();
		Set<String> key2Remove = new HashSet<String>(); 

//		System.out.println("*** Keys ***");
		for (String keyString: keyStrings) {
			ElementPairManager elementPairManager = this.get(keyString);			
			Element element = elementPairManager.getVisitManager().find(keyString);

			if (element == null)
				continue;

			if (element.getCount() <= iThreshold)
				key2Remove.add(keyString);
		}
		
		for (String keyString: key2Remove) {
			this.remove(keyString);
		}
	}

}
