/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

package ca.ubc.cs.salee.classdiagram.classfigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jdt.core.IType;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;

public class UMLNode extends GraphNode {

	IFigure customFigure = null;
	
	public UMLNode(IContainer graphModel, int style, IFigure figure) {
		super(graphModel, style, figure);
	}
		
	protected IFigure createFigureForModel() {
		return (IFigure) this.getData();
	}

	public void setUMLNode(Graph graphModel, IFigure figure) {
		graphModel.changeNode(this, figure);				
	}
	
}