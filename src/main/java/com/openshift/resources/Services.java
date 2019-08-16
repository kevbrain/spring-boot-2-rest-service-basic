package com.openshift.resources;

import java.util.HashMap;

public class Services {

	private String name;
	
	private HashMap<String,ListenPort> listenPorts;
	
	private String type;
	
	private String clusterIP;
	
	private String selectorDC;
	
	private Routes route;
	
	
	public Services(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public HashMap<String, ListenPort> getListenPorts() {
		return listenPorts;
	}

	public void setListenPorts(HashMap<String, ListenPort> listenPorts) {
		this.listenPorts = listenPorts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClusterIP() {
		return clusterIP;
	}

	public void setClusterIP(String clusterIP) {
		this.clusterIP = clusterIP;
	}
	
	

	public String getSelectorDC() {
		return selectorDC;
	}

	public void setSelectorDC(String selectorDC) {
		this.selectorDC = selectorDC;
	}
	
	

	public Routes getRoute() {
		return route;
	}

	public void setRoute(Routes route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "Services [name=" + name + ", listenPorts=" + listenPorts + ", type=" + type + ", clusterIP=" + clusterIP
				+ "]";
	}
	
	
	
	
}

