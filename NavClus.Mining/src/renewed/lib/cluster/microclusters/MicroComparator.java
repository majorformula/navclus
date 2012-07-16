package renewed.lib.cluster.microclusters;

import java.util.Comparator;

public class MicroComparator implements Comparator<MicroPair> {

	@Override
	public int compare(MicroPair arg0, MicroPair arg1) {
		// TODO Auto-generated method stub
		return (arg0).getSimilarity() > (arg1).getSimilarity() ? 0:1;
	}

}
