/*******************************************************************************
Copyright (c) 2010, 2012 Seonah Lee, SA Lab, KAIST
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html *
Contributors:
Seonah Lee - initial implementation
*******************************************************************************/

package navclus.userinterface.classdiagram.classfigure;

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