package renewed.lib.cluster.microclusters;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;

public class TeamTracksMicroClusterManager extends MicroClusterManager {
/////////////////////////////////////////////////////////////////////////////		
	Segment segment = new Segment();

	public void insert2segment(String elementName) {
		if (elementName == null) return;
		if (elementName == "done") {
			segment.segment();
			return;
		}
	}

	int count = 0;
	public void insert2segment(String elementName, String Kind, int n) {	
		if (elementName == null) return;
		if (elementName == "done") {
			segment.segment();
			return;
		}

		segment.setupKeyName(elementName); // only for the initial state
		count++;
		if (count >= n) {
			segment.segment();
			segment.setupKeyName(elementName); // new setting for the key name
			count = 0;
		}

		// visit add...
//		if (Kind.equals("edit") || Kind.equals("Edit"))
//			segment.edit_add(elementName);			
//		else
			segment.visit_add(elementName);
	}
/*
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
*/
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
			// for a visited element
			elementManager.insert(elementName);					
			previousName = elementName;
		}

		private boolean revisited(String elementName) {
			if (elementName.equals(previousName) ) { // if it is continuous...insert the element...
				return false;
			}
			
//			if (elementName.endsWith(".java") || elementName.endsWith(".class") ||  elementName.endsWith(".xml")) {
//				return false;
//			}

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
}
