package navclus.userinterface.classdiagram.classfigure;

import java.util.Set;

import navclus.userinterface.classdiagram.PlugIn;
import navclus.userinterface.classdiagram.java.manager.TypeNode;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


public class ClassFigureCreator {

	Color classColor = new Color(null, 255, 255, 206);
	Font classFont = new Font(null, "Arial", 12, SWT.BOLD);
//	static ImageRegistry ir = new ImageRegistry();
	
	
	public ClassFigureCreator() {
		super();
//		System.out.println("hey");

	}

//	abstract public IFigure createClassFigure(TypeNode node);
	
	public IFigure createClassFigure(TypeNode node)  
	{ 	
		IType itype = node.getType();
		
		if (!itype.exists()) return null;
		
		// salee		
		Label classLabel = null;

		// add class name	
		classLabel = createClassName(itype);		
		if (classLabel == null) return null ; // no action

		classLabel.setFont(classFont);
		ClassFigure classFigure = new ClassFigure(classLabel, classColor);

		// add fields & methods
		addFields(classFigure, node.fields);
		addMethods(classFigure, node.methods);
		
		// finishing the figure
		classFigure.setSize(-1, -1);
		classFigure.setBackgroundColor(classColor);

		return classFigure;
	}
	
	public Label createClassName(IType itype) {
		if (itype != null) {
			try {

				PlugIn plugin;
				
				if (itype.isInterface()) {
					return  new Label(itype.getElementName(), PlugIn.getDefault().getImage("int_obj"));					
				}
				else if (itype.isEnum()) {
					return  new Label(itype.getElementName(), PlugIn.getDefault().getImage("enum_obj"));
				}
				else {
					return  new Label(itype.getElementName(), PlugIn.getDefault().getImage("class_obj"));
				}

			} catch (JavaModelException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public String createMethodName(IMethod imethod) {
		String methodname = null;

		if (imethod != null) {
			methodname = imethod.getElementName() + "(";

			String [] pts = imethod.getParameterTypes();
			int count = pts.length;
			for (String pt: pts) {

				String sParameter =  Signature.toString(pt);

				if (sParameter.contains(".")) {
					int i = sParameter.lastIndexOf('.');
					sParameter = sParameter.substring(i+1, sParameter.length());
				}				
				methodname = methodname.concat(sParameter);
				count--;

				if (count > 0)
					methodname = methodname.concat(", ");
			}

			try {
				String sReturn = Signature.toString(imethod.getReturnType());
				if (sReturn.contains(".")) {
					int i = sReturn.lastIndexOf('.');
					sReturn = sReturn.substring(i+1, sReturn.length());
				}								
				methodname = methodname + ") : " + sReturn;
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		return methodname;
	}
	
	public void addMethods(ClassFigure classFigure, Set<IMethod> Methods) {
		if (Methods.size() == 0) return;

		try {
			for (IMethod imethod: Methods) {
				if (!imethod.exists()) return;

				Label method = null;

				String methodname = createMethodName(imethod);
				if (methodname == null) return;

				int iFlag = imethod.getFlags();
				if (Flags.isPublic(iFlag)) {
					method = new Label(methodname, PlugIn.getDefault().getImage("methpub_obj"));
				}
				else if (Flags.isPrivate(iFlag)) {
					method = new Label(methodname, PlugIn.getDefault().getImage("methpri_obj"));
				}
				else if (Flags.isProtected(iFlag)) {
					method = new Label(methodname, PlugIn.getDefault().getImage("methpro_obj"));
				}
				else {
					method = new Label(methodname, PlugIn.getDefault().getImage("methdef_obj"));
				}					
				classFigure.getMethodsCompartment().add(method);
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	public void addFields(ClassFigure classFigure, Set<IField> Fields) {
		if (Fields.size() == 0) return ;

		try {			
			for (IField ifield: Fields) {
				if (!ifield.exists()) return;

				Label attribute = null;

				String fieldname = ifield.getElementName() + " : " 
								 + Signature.toString(ifield.getTypeSignature());				
				
				int iFlag = ifield.getFlags();
				
				if (Flags.isPublic(iFlag)) {
					attribute = new Label(fieldname, PlugIn.getDefault().getImage("field_public_obj"));				
				}
				else if (Flags.isPrivate(iFlag)) {
					attribute = new Label(fieldname, PlugIn.getDefault().getImage("field_private_obj"));
				}
				else if (Flags.isProtected(iFlag)) {
					attribute = new Label(fieldname, PlugIn.getDefault().getImage("field_protected_obj"));
				}
				else {
					attribute = new Label(fieldname, PlugIn.getDefault().getImage("field_default_obj"));
				}		
				classFigure.getAttributesCompartment().add(attribute);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}	
	}
}
