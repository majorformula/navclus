package old.data.pairs;

import java.util.Collections;
import java.util.Iterator;

import old.data.elements.ElementSimilarityComparator;
import renewed.data.elements.Element;
import renewed.data.elements.ElementManager;



public class ElementPairManager {
	
	int keyNum = 0;	
	ElementManager visitManager = new ElementManager();
	ElementManager visitFileManager = new ElementManager();
	ElementManager changeManager = new ElementManager();
	
	public int getKeyNum() {
		return keyNum;
	}
	public void setKeyNum(int key_num) {
		this.keyNum = key_num;
	}
	public void increaseKeyNum() {
		this.keyNum++;
	}
	
	public ElementManager getVisitManager() {
		return visitManager;
	}
	
	public void setVisitManager(ElementManager visitManager) {
		this.visitManager = visitManager;
	}
	
	public ElementManager getVisitFileManager() {
		return visitFileManager;
	}
	public void setVisitFileManager(ElementManager visitFileManager) {
		this.visitFileManager = visitFileManager;
	}
		
	public ElementManager getChangeManager() {
		return changeManager;
	}
	
	public ElementManager getChangeManager(int minSupport) {
		ElementManager elementManager = new ElementManager();
		
		changeManager.sort();		
		for (int i=0; i< changeManager.size(); i++) {
			Element element = changeManager.elementAt(i);
			
			if (element.getCount() < minSupport) 
				break;
			
			elementManager.add(element);
		}
		
		return elementManager;
	}
	
	public void setChangeManager(ElementManager changeManager) {
		this.changeManager = changeManager;
	}

	public void clear() {
		visitManager.clear();
		visitFileManager.clear();
		changeManager.clear();
	}
	
	public void merge(ElementPairManager pairmanager) {
		if (pairmanager == null) return; // 원래는 한 곳에만 속해야 하지만....지금 중복으로 널이 나오는 거야
		
		this.keyNum = this.keyNum + pairmanager.getKeyNum();
		visitManager.merge(pairmanager.getVisitManager());
		visitFileManager.merge(pairmanager.getVisitFileManager());
		changeManager.merge(pairmanager.getChangeManager());
	}
	
	public void sort() {
		visitManager.sort();
		changeManager.sort();		
	}

}
