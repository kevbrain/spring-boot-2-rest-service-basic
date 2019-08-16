package com.openshift.compare;

import com.openshift.resources.Services;

public class ServiceCompare {

	private Services serviceA;
	
	private Services serviceB;
	
	private RouteCompare routeCompare;
	
	

	public ServiceCompare(Services serviceA, Services serviceB) {
		super();
		this.serviceA = serviceA;
		this.serviceB = serviceB;
		
	}

	public void generateRoutesCompare() {
		routeCompare = new RouteCompare(serviceA!=null?serviceA.getRoute():null, serviceB!=null?serviceB.getRoute():null);
	}
	
	public Services getServiceA() {
		return serviceA;
	}

	public void setServiceA(Services serviceA) {
		this.serviceA = serviceA;
	}

	public Services getServiceB() {
		return serviceB;
	}

	public void setServiceB(Services serviceB) {
		this.serviceB = serviceB;
	}

	public RouteCompare getRouteCompare() {
		return routeCompare;
	}

	public void setRouteCompare(RouteCompare routeCompare) {
		this.routeCompare = routeCompare;
	}
	
	
	
}
