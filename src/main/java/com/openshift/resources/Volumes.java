package com.openshift.resources;

public class Volumes {
	
	private String name;
	
	private Secret secret;
	
	private Hostpath hostpath;
	
	private PersistentVolume claim;
	
	private ConfigMap configMap;

	public Volumes(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Secret getSecret() {
		return secret;
	}

	public void setSecret(Secret secret) {
		this.secret = secret;
	}

	public Hostpath getHostpath() {
		return hostpath;
	}

	public void setHostpath(Hostpath hostpath) {
		this.hostpath = hostpath;
	}

	public PersistentVolume getClaim() {
		return claim;
	}

	public void setClaim(PersistentVolume claim) {
		this.claim = claim;
	}

	public ConfigMap getConfigMap() {
		return configMap;
	}

	public void setConfigMap(ConfigMap configMap) {
		this.configMap = configMap;
	}
	
	
	

}
