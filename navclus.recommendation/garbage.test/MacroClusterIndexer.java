package renewed.lib.cluster.macroclusters;

import java.util.Vector;

import renewed.data.clusters.IJPair;
import renewed.data.clusters.IList;
import renewed.data.clusters.MatrixPairManager;
import renewed.data.matrix.MatrixManager;
import renewed.data.matrix.ST;

public class MacroClusterIndexer {

	Vector<IList> vector = new Vector<IList>();
	double iThreshold;
	
	int lastId = 0;

	public MacroClusterIndexer(double iThreshold) {
		if (iThreshold > 1.0)
			System.err.println("it is out of scope in setting a threshold!");

		this.iThreshold = iThreshold;
	}

	public void setVector(Vector<IList> vector) {
		this.vector = vector;
	}

	public Vector<IList> getVector() {
		return vector;
	}

	public boolean add(IJPair e) {
		if (this.contains(e.getI())) {
			IList iList = this.find(e.getI());

			if (! iList.contains(e.getJ())) {
				iList.modified();
				iList.add(e.getJ());				
			}			
		}
		else if (this.contains(e.getJ())) {
			IList iList = this.find(e.getJ());

			if (! iList.contains(e.getI())) {
				iList.modified();
				iList.add(e.getI());
			}
		}
		else { // add the pair as a list
			IList iList = new IList();
			iList.setId(++lastId);
			iList.inserted();
			iList.add(e.getI());
			iList.add(e.getJ());

			vector.add(iList);			
		}
		return false;
	}

	public boolean contains(int i) {
		for (IList iList: vector) {
			for (Integer integer: iList.getiList()) {
				int value = integer.intValue();

				if (value == i) {
					return true;
				}
			}
		}			
		return false;
	}

	public IList find(int i) {
		for (IList iList: vector) {
			for (Integer integer: iList.getiList()) {
				int value = integer.intValue();

				if (value == i) {
					return iList;
				}
			}
		}			
		return null;
	}

	public void clear() {
		vector.clear();
	}


	public void print() {
		for (IList iList: vector) {
			System.out.print(iList.getId() +":	");
			System.out.print(iList.getUpdated() +": ");
			System.out.print(iList);
			System.out.println();
		}		
	}

	public void clearStatus() {
		for (IList iList: vector) {
			iList.cleared();
		}		
	}

	public int size() {
		return vector.size();
	}

	public void cluster(MatrixManager matrixManager) {
		MatrixPairManager pairManager = new MatrixPairManager();

		for (int i = 0; i < matrixManager.getLength(); i++) {
			ST<Integer, Double> st = matrixManager.getRows(i).getSt();	
			for (int j : st) {
				if (i == j) {
					continue;
				}
				// cluster j to i
				pairManager.add(new IJPair(i, j));
			}
		}

		//		System.out.println("size" + pairManager.size()); // 여기까지 잘 진행되어 왔다.
		for (IJPair pair: pairManager.getPairs()) {
			//			pair.print();
			this.add(pair);
		}
	}
}
