package com.openshift.compare;

import java.util.HashMap;

import com.openshift.resources.OpenshiftProject;

public class ProjectCompare {

	private String projectAName;
	
	private String projectBName;
	
	private OpenshiftProject projectA;
	
	private OpenshiftProject projectB;
	
	private HashMap<String,DCCompare> dcCompare;
	
	private HashMap<String,RouteCompare> routeCompare;
	
	private HashMap<String,ServiceCompare> serviceCompare;
	
	private HashMap<String,SecretCompare> secretCompare;
	
	private HashMap<String,ConfigMapCompare> configMapCompare;
	
	private HashMap<String,PersistentVolumeCompare> persistentVolumeCompare;
	

	public ProjectCompare(OpenshiftProject projectA, OpenshiftProject projectB) {
		super();
		this.projectA = projectA;
		this.projectB = projectB;
		this.projectAName = projectA!=null?projectA.getProjectName():null;
		this.projectBName = projectB!=null?projectB.getProjectName():null;
		
	}

	public String getProjectAName() {
		return projectAName;
	}

	public void setProjectAName(String projectAName) {
		this.projectAName = projectAName;
	}

	public String getProjectBName() {
		return projectBName;
	}

	public void setProjectBName(String projectBName) {
		this.projectBName = projectBName;
	}

	public HashMap<String, DCCompare> getDcCompare() {
		return dcCompare;
	}

	public void setDcCompare(HashMap<String, DCCompare> dcCompare) {
		this.dcCompare = dcCompare;
	}

	public OpenshiftProject getProjectA() {
		return projectA;
	}

	public void setProjectA(OpenshiftProject projectA) {
		this.projectA = projectA;
	}

	public OpenshiftProject getProjectB() {
		return projectB;
	}

	public void setProjectB(OpenshiftProject projectB) {
		this.projectB = projectB;
	}

	public HashMap<String, RouteCompare> getRouteCompare() {
		return routeCompare;
	}

	public void setRouteCompare(HashMap<String, RouteCompare> routeCompare) {
		this.routeCompare = routeCompare;
	}

	public HashMap<String, ServiceCompare> getServiceCompare() {
		return serviceCompare;
	}

	public void setServiceCompare(HashMap<String, ServiceCompare> serviceCompare) {
		this.serviceCompare = serviceCompare;
	}

	public HashMap<String, SecretCompare> getSecretCompare() {
		return secretCompare;
	}

	public void setSecretCompare(HashMap<String, SecretCompare> secretCompare) {
		this.secretCompare = secretCompare;
	}

	public HashMap<String, ConfigMapCompare> getConfigMapCompare() {
		return configMapCompare;
	}

	public void setConfigMapCompare(HashMap<String, ConfigMapCompare> configMapCompare) {
		this.configMapCompare = configMapCompare;
	}

	public HashMap<String, PersistentVolumeCompare> getPersistentVolumeCompare() {
		return persistentVolumeCompare;
	}

	public void setPersistentVolumeCompare(HashMap<String, PersistentVolumeCompare> persistentVolumeCompare) {
		this.persistentVolumeCompare = persistentVolumeCompare;
	}
	
	
	
}
