/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package old.data.matrix;


public class MatrixManager {
	
	SparseMatrix matrix; 

	public MatrixManager(int i) {
		matrix = new SparseMatrix(i);		
	}
	
	public void setMatrix(int i, int j, double value) {
		matrix.put(i, j, value);
	}
	
	public SparseMatrix getMatrix() {
		return matrix;
	}	
	
	public void print() {
        	matrix.println();
	}
	
    public int getLength() {
		return matrix.getLength();
	}
    
    public SparseVector getRows(int i) {
		return  matrix.getRows(i);
	}
}
