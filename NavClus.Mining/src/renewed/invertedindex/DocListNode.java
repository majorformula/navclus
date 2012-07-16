package renewed.invertedindex;

import java.io.Serializable;

/**
 *
 * @author ANSHUMAN
 */
public class DocListNode implements Comparable {
    Integer docId;
    Integer frequencyInDoc;

	double similarity;

    DocListNode(Integer docId, Integer freq)
    {
    this.docId = docId;
    this.frequencyInDoc = freq;

    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer DocId) {
        this.docId = DocId;
    }

    public Integer getFrequencyInDoc() {
        return frequencyInDoc;
    }

    public void setFrequencyInDoc(Integer frequencyInDoc) {
        this.frequencyInDoc = frequencyInDoc;
    }

    public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
    
    @Override
    public String toString()
    {
        return "[docId="+getDocId()+"freq="+getFrequencyInDoc()+"]";
    }

	@Override
	public int compareTo(Object arg0) {
		if (this.getDocId() > ((DocListNode) arg0).getDocId())
			return 1;
		else if (this.getDocId() > ((DocListNode) arg0).getDocId())
			return 01;
		else
			return 0;
	}

}