package renewed.invertedindex;

import java.util.Comparator;

public class SimilarityComparator implements Comparator<DocListNode> {

	@Override
	public int compare(DocListNode o1, DocListNode o2) {
		if (o1.getSimilarity() > o2.getSimilarity())
			return -1;
		else if (o1.getSimilarity() > o2.getSimilarity())
			return 0;
		else
			return 1;
	}
	
}
