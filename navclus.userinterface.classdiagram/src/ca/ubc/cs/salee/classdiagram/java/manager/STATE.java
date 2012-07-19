package ca.ubc.cs.salee.classdiagram.java.manager;

public enum STATE {
	noChange (0), 
	toCreate (1), 
	toUpdate (2);
	
    STATE(int value) { this.value = value; }

    private final int value;

    public int value() { return value; }
}
