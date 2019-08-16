package com.openshift.resources;

public class TriggerImageChange {

	private String namespace;
	
	private String image;
	
	private String tag;
	
	

	public TriggerImageChange(String namespace, String image, String tag) {
		super();
		this.namespace = namespace;
		this.image = image;
		this.tag = tag;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
