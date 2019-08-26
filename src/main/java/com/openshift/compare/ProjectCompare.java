package com.openshift.compare;

import java.util.HashMap;
import java.util.Map;

import com.openshift.resources.OpenshiftProject;

public class ProjectCompare {

	private String projectAName;
	
	private String projectBName;
	
	private OpenshiftProject projectA;
	
	private OpenshiftProject projectB;
	
	private Map<String,DCCompare> dcCompare;
	
	private Map<String,RouteCompare> routeCompare;
	
	private Map<String,ServiceCompare> serviceCompare;
	
	private Map<String,SecretCompare> secretCompare;
	
	private Map<String,ConfigMapCompare> configMapCompare;
	
	private Map<String,PersistentVolumeCompare> persistentVolumeCompare;
	

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

	public Map<String, DCCompare> getDcCompare() {
		return dcCompare;
	}

	public void setDcCompare(Map<String, DCCompare> dcCompare) {
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

	public Map<String, RouteCompare> getRouteCompare() {
		return routeCompare;
	}

	public void setRouteCompare(Map<String, RouteCompare> routeCompare) {
		this.routeCompare = routeCompare;
	}

	public Map<String, ServiceCompare> getServiceCompare() {
		return serviceCompare;
	}

	public void setServiceCompare(Map<String, ServiceCompare> serviceCompare) {
		this.serviceCompare = serviceCompare;
	}

	public Map<String, SecretCompare> getSecretCompare() {
		return secretCompare;
	}

	public void setSecretCompare(Map<String, SecretCompare> secretCompare) {
		this.secretCompare = secretCompare;
	}

	public Map<String, ConfigMapCompare> getConfigMapCompare() {
		return configMapCompare;
	}

	public void setConfigMapCompare(Map<String, ConfigMapCompare> configMapCompare) {
		this.configMapCompare = configMapCompare;
	}

	public Map<String, PersistentVolumeCompare> getPersistentVolumeCompare() {
		return persistentVolumeCompare;
	}

	public void setPersistentVolumeCompare(HashMap<String, PersistentVolumeCompare> persistentVolumeCompare) {
		this.persistentVolumeCompare = persistentVolumeCompare;
	}
	
	
	
}
