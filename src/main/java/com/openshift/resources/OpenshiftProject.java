package com.openshift.resources;

import java.util.HashMap;

public class OpenshiftProject {

	
	private String projectName;
	
	private HashMap<String, DeploymentConfig> deploymentConfigs;
	
	private HashMap<String, Services> services;
	
	private HashMap<String, Routes> routes;
	
	private HashMap<String, ConfigMap> configMaps;
	
	

	public OpenshiftProject(String projectName) {
		super();
		this.projectName = projectName;
		this.deploymentConfigs = new HashMap<>();
		this.services = new HashMap<>();
		this.routes = new HashMap<>();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public HashMap<String, DeploymentConfig> getDeploymentConfigs() {
		return deploymentConfigs;
	}

	public void setDeploymentConfigs(HashMap<String, DeploymentConfig> deploymentConfigs) {
		this.deploymentConfigs = deploymentConfigs;
	}

	public HashMap<String, Services> getServices() {
		return services;
	}

	public void setServices(HashMap<String, Services> services) {
		this.services = services;
	}

	public HashMap<String, Routes> getRoutes() {
		return routes;
	}

	public void setRoutes(HashMap<String, Routes> routes) {
		this.routes = routes;
	}

	public HashMap<String, ConfigMap> getConfigMaps() {
		return configMaps;
	}

	public void setConfigMaps(HashMap<String, ConfigMap> configMaps) {
		this.configMaps = configMaps;
	}

	
	
	
}
