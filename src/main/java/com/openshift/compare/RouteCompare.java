package com.openshift.compare;

import com.openshift.resources.Routes;

public class RouteCompare extends ResourceCompare{
	
	private Routes routeA;
	
	private Routes routeB;

	public RouteCompare(Routes routeA, Routes routeB) {
		super(routeA,routeB);
		this.routeA = routeA;
		this.routeB = routeB;
	}

	public Routes getRouteA() {
		return routeA;
	}

	public void setRouteA(Routes routeA) {
		this.routeA = routeA;
	}

	public Routes getRouteB() {
		return routeB;
	}

	public void setRouteB(Routes routeB) {
		this.routeB = routeB;
	}
	
	public void compare() {
		
		if (routeA!=null && routeB!=null) {
			// just for test
			routeB.setHost(routeA.getHost().replace(".com", ".lu"));
			routeB.setHost(compareValue(routeB.getHost(),routeA.getHost()));
			
		}
	}
	
	
	

}
