package com.openshift.resources;

public class ValueFrom {
	
	private String type;
	
	private String secretKeyRef;
	
	private String key;

	public ValueFrom(String type, String secretKeyRef, String key) {
		super();
		this.type = type;
		this.secretKeyRef = secretKeyRef;
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecretKeyRef() {
		return secretKeyRef;
	}

	public void setSecretKeyRef(String secretKeyRef) {
		this.secretKeyRef = secretKeyRef;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
