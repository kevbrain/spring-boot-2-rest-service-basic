package com.openshift.resources;

import java.util.HashMap;

public class InstanceOpenShift {

	private String name;
	
	private String url;
	
	private String token;	
	
	private HashMap<String, OpenshiftProject> projects;

	public InstanceOpenShift(String name,String url, String token) {
		super();
		this.name = name;
		this.url = url;
		this.token = token;
	}
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public HashMap<String, OpenshiftProject> getProjects() {
		return projects;
	}

	public void setProjects(HashMap<String, OpenshiftProject> projects) {
		this.projects = projects;
	}
	
	
	
}
