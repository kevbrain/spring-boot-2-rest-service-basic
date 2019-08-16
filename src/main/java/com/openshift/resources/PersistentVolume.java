package com.openshift.resources;

import org.json.JSONObject;

public class PersistentVolume {
	
	private String name;
	
	private String capacity;
	
	private String hostPath;
	
	private String[] accesMode;
	
	private String claimPolicy;
	

	

	public PersistentVolume(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getHostPath() {
		return hostPath;
	}

	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}

	public String[] getAccesMode() {
		return accesMode;
	}

	public void setAccesMode(String[] accesMode) {
		this.accesMode = accesMode;
	}

	public String getClaimPolicy() {
		return claimPolicy;
	}

	public void setClaimPolicy(String claimPolicy) {
		this.claimPolicy = claimPolicy;
	}

	

}
