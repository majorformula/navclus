package renewed.lib.cluster.microclusters;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;

public class MicroClusterManager {
	
	HashMap<String, ElementManager> hashmap = new HashMap<String, ElementManager> ();
	
	public HashMap<String, ElementManager> getHashmap() {
		return hashmap;
	}

	public void setHashmap(HashMap<String, ElementManager> hashmap) {
		this.hashmap = hashmap;
	}

	public void clear() {
		hashmap.clear();
	}

	public Object clone() {
		return hashmap.clone();
	}

	public boolean containsKey(Object key) {
		return hashmap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return hashmap.containsValue(value);
	}

	public Set<Entry<String, ElementManager>> entrySet() {
		return hashmap.entrySet();
	}

	public boolean equals(Object o) {
		return hashmap.equals(o);
	}

	public ElementManager get(Object key) {
		return hashmap.get(key);
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

	public ElementManager put(String key, ElementManager value) {
		return hashmap.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends ElementManager> m) {
		hashmap.putAll(m);
	}

	public ElementManager remove(Object key) {
		return hashmap.remove(key);
	}

	public int size() {
		return hashmap.size();
	}

	public String toString() {
		return hashmap.toString();
	}

	public Collection<ElementManager> values() {
		return hashmap.values();
	}	

/////////////////////////////////////////////////////////////////////////////		
	Segment segment = new Segment();

	public void insert2segment(String elementName) {
		if (elementName == null) return;
		if (elementName == "done") {
			segment.segment();
			return;
		}

		segment.setupKeyName(elementName); // only for the initial state

		if (segment.revisited(elementName)) {
			segment.segment();
			segment.setupKeyName(elementName); // new setting for the key name			
		}

		// visit add...
		segment.visit_add(elementName);
	}
	
	public void insert2segment(String elementName, String Kind) {
		if (elementName == null) return;
		if (elementName == "done") {
			segment.segment();
			return;
		}

		segment.setupKeyName(elementName); // only for the initial state
		if (segment.revisited(elementName)) {
			segment.segment();
			segment.setupKeyName(elementName); // new setting for the key name			
		}

		// visit add...
		if (Kind.equals("edit") || Kind.equals("Edit"))
			segment.edit_add(elementName);			
		else
			segment.visit_add(elementName);
	}


	class Segment {

		// for insert2segment
		ElementManager elementManager = new ElementManager(); // for insert2segment
		String keyName = null;
		String previousName = null ; // for insert2segment

		void setupKeyName(String elementName) {
			if ((previousName == null) /*&& (elementName.contains("*.java")) */) { 
				keyName = elementName; // it is the initial state
				//				elementManager.increaseKeyNum();
			}
		}
		
		void edit_add(String elementName) {
			// for a visited element
			elementManager.insert(elementName, 1, true);					
			previousName = elementName;
		}

		void visit_add(String elementName) {
			if (elementName.equals(previousName) ) { // if it is continuous...insert the element...
				return;
			}
			
			// for a visited element
			elementManager.insert(elementName);					
			previousName = elementName;
		}

		private boolean revisited(String elementName) {
			if (elementName.equals(previousName) ) { // if it is continuous...insert the element...
				return false;
			}
			
			if (elementName.endsWith(".java") || elementName.endsWith(".class") ||  elementName.endsWith(".xml")) {
				return false;
			}

			Element currentNode = elementManager.find(elementName); // if it is new...insert the element...
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
				ElementManager storedElementManager = get(keyName);
				storedElementManager.merge(elementManager);
				storedElementManager.modified();								
				put(keyName, storedElementManager); //*** put here in the existing element manager! 					
			}
			else	{ // if no key --> insert
				if (keyName != null) {
					elementManager.inserted();
					put(keyName, elementManager); //*** put here in the new element manager!
				}
			}		

			elementManager = new ElementManager();			
			segment_clear();
		}

		public void segment_clear() {
			elementManager.clear() ; // for insert2segment ... 2 clear
			keyName = null;
			previousName = null ; // for insert2segment
		}
	}

	public void printSummary() {		
		int none = 0;
		int modified = 0;
		int inserted = 0;		
		Set<String> keyStrings = this.keySet();
		for (String keyString: keyStrings) {
			ElementManager elementManager = this.get(keyString);
			int updated = elementManager.getUpdated();
			switch (updated) {
			case 0:
				none++;
				break;
			case 1:
//				System.out.println("modified: " + elementManager.size() +": " + keyString);
				modified++;
				break;
			case 2:
//				System.out.println("inserted --" + elementManager.size() +": " + keyString);	
				inserted++;
				break;
			default:
				System.out.println("error");
			}
	
////		elementManager.println(iThreshold);
		}
		System.out.print("#micro-clusters:" + this.size() +",	");		
		System.out.print("#nochange:" + none + ",	");
		System.out.print("#modified:" + modified + ",	");
		System.out.println("#inserted:" + inserted);
//		this.printIndex();
	}
	
	public void clearStatus() {			
		Set<String> keyStrings = this.keySet();
		for (String keyString: keyStrings) {
			ElementManager elementManager = this.get(keyString);
			elementManager.cleared();
		}
	}
	
	public void updateIntertedStatus() {			
		Set<String> keyStrings = this.keySet();
		for (String keyString: keyStrings) {
			ElementManager elementManager = this.get(keyString);
			elementManager.inserted();
		}
	}	
	
	public void println() {		
		Set<String> keyStrings = this.keySet();

		System.out.println("*** Clusters ***");
		for (String keyString: keyStrings) {
			System.out.println("key:" + keyString);
			ElementManager elementManager = this.get(keyString);
			elementManager.println();
			System.out.println();
		}
	}
	
	public void println(int iThreshold) {		
		Set<String> keyStrings = this.keySet();

		System.out.println("*** Clusters ***");
		for (String keyString: keyStrings) {
			System.out.println("key:" + keyString);
			ElementManager elementManager = this.get(keyString);
			elementManager.println(iThreshold);
		}
	}

	public void printKeys() {		
		Set<String> keyStrings = this.keySet();

		System.out.println("*** Keys ***");
		for (String keyString: keyStrings) {
			ElementManager elementManager = this.get(keyString);			
			Element element = elementManager.find(keyString);

			if (element != null)
				System.out.println(" : " + element.getCount() +  " : " + element.getName() );
		}
	}
	
	public void printKeys(int nFreq) {		
		Set<String> keyStrings = this.keySet();

		System.out.println("*** Keys ***");
		for (String keyString: keyStrings) {
			ElementManager elementManager = this.get(keyString);			
			Element element = elementManager.find(keyString);

			if (element != null)
				if (element.getCount() >= nFreq) 
					System.out.println(" : " + element.getCount() +  " : " + element.getName() );
		}
	}

	public void ignore(int iThreshold) {		
		Set<String> keyStrings = this.keySet();
		Set<String> key2Remove = new HashSet<String>(); 

//		System.out.println("*** Keys ***");
		for (String keyString: keyStrings) {
			ElementManager elementManager = this.get(keyString);			
			Element element = elementManager.find(keyString);

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
