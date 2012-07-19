package renewed.data.elements;

import java.io.PrintStream;


public class Element {
	String name;
	int count;
	boolean bEdit;
	
	int doc;
	double TF ;
	double IDF ; 
	double TFIDF ;
	int X;
	int Y;
	double XTFIDF;
	double YTFIDF;
	double weight = 1.0;
	double similarity;

	public Element(String name) {
		this.name = name;
		this.count = 1;
		this.doc = 1;
	}
	
	public Element(String name, int count) {
		this.name = name;
		this.count = count;
		this.doc = 1;
	}

	public Element(String name, int count, boolean edit) {
		this.name = name;
		this.count = count;
		this.doc = 1;
		this.bEdit = edit;
	}
	
	public Element(String name, double similarity) {
		this.name = name;
		this.similarity = similarity;
	}
	
	
	
	public boolean isEdit() {
		return bEdit;
	}

	public void setEdit(boolean bEdit) {
		if (bEdit == true)
			this.bEdit = bEdit;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increase() {
		count++;
	}
	
	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	public int getDoc() {
		return doc;
	}

	public void setDoc(int doc) {
		this.doc = doc;
	}
	
	public double getIDF() {
		return IDF;
	}

	public void setIDF(double iDF) {
		IDF = iDF;
	}

	public double getTFIDF() {
		return TFIDF;
	}

	public void setTFIDF(double tFIDF) {
		TFIDF = tFIDF;
	}
	
	public double getXTFIDF() {
		return XTFIDF;
	}

	public void setXTFIDF(double xTFIDF) {
		XTFIDF = xTFIDF;
	}

	public double getYTFIDF() {
		return YTFIDF;
	}

	public void setYTFIDF(double yTFIDF) {
		YTFIDF = yTFIDF;
	}
	
	public double idf(int Total) {
		if (doc == 0)
			this.IDF = logB(Total / 1, 2);
		else
			this.IDF = logB(Total / doc, 2);
		
		return IDF;
	}
	
	public double tf(double sums) {
		TF = ((double) this.getCount() / sums);  		
		return TF;
	}
	
	public double tfidf() {
		return TFIDF = TF * IDF; 
	}
	
	public void println() {
//		IJavaElement firstElement = createElement(getName());
		
//		if ((firstElement != null))
//			System.out.println(count + ", " + doc + ", " + IDF + ", " + TFIDF + ", " + getName() /* firstElement.getElementName() */);
		
		System.out.println(bEdit + ", " + count + ", " + weight + ", " + getName() /* firstElement.getElementName() */);
	}
	
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
	
	public void println(PrintStream outfile) {
//		IJavaElement firstElement = createElement(getName());
		
//		if ((firstElement != null))
			outfile.println(count + ", " + getName() /* firstElement.getElementName() */);
	}
	
	
//	private IJavaElement createElement(String structureHandler) {
//		IJavaElement element = JavaCore.create(structureHandler);
//
//		if (element == null) {
//			return null;
//		}
//
//		if (!element.exists()) {
//			return null;
//		}
//
//		return element;
//	}

	public static double logB(double x, double base) {
		return Math.log(x) / Math.log(base);
	}
}
