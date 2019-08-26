package com.openshift.resources;


import java.util.HashMap;
import java.util.Map;

public class Containers {
	
	private String name;

	private Map<String, Object> env;
	
	private Map<String, VolumeMount> volmount;
	
	public Containers(String name) {
		super();
		this.name = name;
		this.env = new HashMap<String, Object>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getEnv() {
		return env;
	}

	public void setEnv(Map<String, Object> env) {
		this.env = env;
	}

	public Map<String, VolumeMount> getVolmount() {
		return volmount;
	}

	public void setVolmount(Map<String, VolumeMount> volmount) {
		this.volmount = volmount;
	}
	
	
	

}
