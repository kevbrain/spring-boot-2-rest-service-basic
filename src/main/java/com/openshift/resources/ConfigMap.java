package com.openshift.resources;

import java.util.HashMap;

public class ConfigMap {
	
	private String name;
	
	private HashMap<String, String> data;

	public ConfigMap(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
	
	
	

}
