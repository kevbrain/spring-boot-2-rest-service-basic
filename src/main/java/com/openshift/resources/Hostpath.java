package com.openshift.resources;

public class Hostpath {

	private String path;
	
	private String type;

	public Hostpath(String path, String type) {
		super();
		this.path = path;
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
