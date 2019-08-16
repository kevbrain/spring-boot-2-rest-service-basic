package com.openshift.resources;

import java.util.HashMap;

public class DeploymentConfig {

	private String name;
	
	private HashMap<String, Containers> containers;
	
	private HashMap<String, Volumes> volumes;
	
	private HashMap<String, TriggerImageChange> triggerImageChange;
	
	private Services servicesLinked;
	
	public DeploymentConfig(String name) {
		super();
		this.name = name;
		this.containers = new HashMap<>();
		this.volumes = new HashMap<>();
		this.triggerImageChange = new HashMap<>();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Containers> getContainers() {
		return containers;
	}

	public void setContainers(HashMap<String, Containers> containers) {
		this.containers = containers;
	}

	public HashMap<String, Volumes> getVolumes() {
		return volumes;
	}

	public void setVolumes(HashMap<String, Volumes> volumes) {
		this.volumes = volumes;
	}

	public HashMap<String, TriggerImageChange> getTriggerImageChange() {
		return triggerImageChange;
	}

	public void setTriggerImageChange(HashMap<String, TriggerImageChange> triggerImageChange) {
		this.triggerImageChange = triggerImageChange;
	}

	public Services getServicesLinked() {
		return servicesLinked;
	}

	public void setServicesLinked(Services servicesLinked) {
		this.servicesLinked = servicesLinked;
	}

	
	
	
	
}
