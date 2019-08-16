package com.openshift.compare;

public class EnvCompare {
	
		
	private Object valueA;
	
	private Object valueB;

	public EnvCompare( Object valueA, Object valueB) {
		super();		
		this.valueA = valueA;
		this.valueB = valueB;
	}

	
	public Object getValueA() {
		return valueA;
	}

	public void setValueA(Object valueA) {
		this.valueA = valueA;
	}

	public Object getValueB() {
		return valueB;
	}

	public void setValueB(Object valueB) {
		this.valueB = valueB;
	}
	
	

}
