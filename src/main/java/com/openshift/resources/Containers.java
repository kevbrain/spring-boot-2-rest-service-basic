package com.openshift.resources;

import java.util.HashMap;

public class Containers {
	
	private String name;

	private HashMap<String, Object> env;
	
	private HashMap<String, VolumeMount> volmount;
	
	public Containers(String name) {
		super();
		this.name = name;
		this.env = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Object> getEnv() {
		return env;
	}

	public void setEnv(HashMap<String, Object> env) {
		this.env = env;
	}

	public HashMap<String, VolumeMount> getVolmount() {
		return volmount;
	}

	public void setVolmount(HashMap<String, VolumeMount> volmount) {
		this.volmount = volmount;
	}
	
	
	

}
