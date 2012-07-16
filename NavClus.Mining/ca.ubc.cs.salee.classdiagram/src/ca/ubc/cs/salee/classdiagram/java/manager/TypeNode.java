package ca.ubc.cs.salee.classdiagram.java.manager;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TypeNode {
	
	private CompilationUnit cu = null;	
	private IType type;
	public Set<IField>  fields; 	// salee
	public Set<IMethod> methods; // salee
	public Set<IType>   embeddedTypes;   // salee
	
//	boolean isDirty;
	STATE state;

	public TypeNode(IType type) {
		this.type = type;
		this.fields = new LinkedHashSet<IField>();    // salee
		this.methods = new LinkedHashSet<IMethod>();  // salee
		this.embeddedTypes = new LinkedHashSet<IType>(); 	 // salee

//		this.isDirty = true;
		this.state = STATE.toCreate;
	}	
	
	public IType getType() {
		return type;
	}

	public void setType(IType type) {
		this.type = type;
	}

//	public boolean isDirty() {
//		return isDirty;
//	}
//
//	public void setDirty(boolean isDirty) {
//		this.isDirty = isDirty;
//	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE nodestate) {
		this.state = nodestate;
	}

	public CompilationUnit getCu() {
		return cu;
	}

	public void setCu(CompilationUnit cu) {
		this.cu = cu;
	}
	
	public boolean contain(IField inputfield) {
		
		for (IField field: this.fields) {
			if (field.getHandleIdentifier().equals(inputfield.getHandleIdentifier())) {
				return true;
			}
		}		
		return false;
	}
	
	public boolean contain(IMethod inputmethod) {
		
		for (IMethod method: this.methods) {
			if (method.getHandleIdentifier().equals(inputmethod.getHandleIdentifier())) {
				return true;
			}
		}
		return false;
	}

	public boolean contain(IType inputtype) {
		
		for (IType type: this.embeddedTypes) {
			if (type.getHandleIdentifier().equals(inputtype.getHandleIdentifier())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addField(IField inputfield) {
		if (this.contain(inputfield))
			return false;

		this.fields.add((IField) inputfield);
//		this.setDirty(true);
		this.setState(STATE.toUpdate);
		return true;
	}

	public boolean addMethod(IMethod inputmethod) {
		if (this.contain(inputmethod))
			return false;

		this.methods.add((IMethod) inputmethod);
//		this.setDirty(true);
		this.setState(STATE.toUpdate);
		return true;
	}
	
	public boolean removeField(IField inputfield) {
		if (this.contain(inputfield)) {			
			this.fields.remove((IField) inputfield);
//			this.setDirty(true);
			this.setState(STATE.toUpdate);
			return true;
		}
		else
			return false;
	}

	public boolean removeMethod(IMethod inputmethod) {
		if (this.contain(inputmethod)) {		
			this.methods.remove((IMethod) inputmethod);
//			this.setDirty(true);
			this.setState(STATE.toUpdate);
			return true;
		}
		else
			return false;
	}
	
	public boolean removeType(IType inputtype) {
		if (this.contain(inputtype)) {
			this.embeddedTypes.remove((IType) inputtype);
//			this.setDirty(true);
			this.setState(STATE.toUpdate);
			return true;
		}
		else
			return false;
	}
	
	
	public void clear() {		
		this.type = null;
		this.cu = null;
		this.fields.clear();    // salee
		this.methods.clear();   // salee
		this.embeddedTypes.clear();     // salee		
	}
}
