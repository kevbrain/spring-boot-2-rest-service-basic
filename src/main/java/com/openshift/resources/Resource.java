package com.openshift.resources;

public class Resource {

	private String resourceKey;
	
	private String resourceType;
	
	private String selected;
	
	

	public Resource(String resourceKey, String resourceType,String selected) {
		super();
		this.resourceKey = resourceKey;
		this.resourceType = resourceType;
		this.selected = selected;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	
}
