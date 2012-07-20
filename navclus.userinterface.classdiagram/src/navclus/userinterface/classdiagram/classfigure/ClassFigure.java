/**
 * 
 */
package navclus.userinterface.classdiagram.classfigure;


import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class ClassFigure extends Figure {

	private CompartmentFigure attributeFigure = new CompartmentFigure();
	private CompartmentFigure methodFigure = new CompartmentFigure();	
	String classname;

	public ClassFigure(Label name, Color classColor) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(classColor);
		setOpaque(true);

		add(name);
		add(attributeFigure);
		add(methodFigure);
		setName(name.getText());
	}

	public CompartmentFigure getAttributesCompartment() {
		return attributeFigure;
	}

	public CompartmentFigure getMethodsCompartment() {
		return methodFigure;
	}
	
	public String getName() {
		return classname;
	}

	public void setName(String name) {
		this.classname = name;
	}
}