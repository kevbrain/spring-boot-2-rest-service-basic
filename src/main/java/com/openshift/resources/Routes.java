package com.openshift.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

public class Routes extends Resource{
	
	public static final String TYPE = "routes";
	
	public static final String API= "/oapi/v1/";
	
	public static final String PATHAPI = API+"namespaces/";
	
	private String name;
	
	private String host;
	
	private String tls_insecureEdgeTerminationPolicy;
	
	private String tls_termination;
	
	private String service;

	public Routes(String name) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;		
	}
	
	public Routes(String name, String host) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;
		this.host = host;
	}

	public static Map<String, Routes> getMapRoutes(InstanceOpenShift openshift,String projectName) {
		
		HashMap<String, Routes> routes = new HashMap<>();
		 try {			 	
			 	
			    JSONArray results = loadItemsResource(buildUrl(openshift,projectName,PATHAPI,API,TYPE), openshift);			
			
				for (int j=0;j<results.length();j++) {
					
					Routes route = extractRoute(results.getJSONObject(j));
					routes.put(route.getName(),route);
				}
				
			} catch (Exception e) {
				logger.log(Level.INFO,e.getMessage());
			}
		
		return routes;
		
		
	}

	private static Routes extractRoute(JSONObject extract) {
	
		JSONObject itemsroute = extract.getJSONObject("metadata");
		String routeName = itemsroute.getString("name");		
		String host = extract.getJSONObject("spec").getString("host");
		Routes route = new Routes(routeName,host);
		
		String service = extract.getJSONObject("spec").getJSONObject("to").getString("name");
		
		route.setService(service);
			
		return route;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getTls_insecureEdgeTerminationPolicy() {
		return tls_insecureEdgeTerminationPolicy;
	}

	public void setTls_insecureEdgeTerminationPolicy(String tls_insecureEdgeTerminationPolicy) {
		this.tls_insecureEdgeTerminationPolicy = tls_insecureEdgeTerminationPolicy;
	}

	public String getTls_termination() {
		return tls_termination;
	}

	public void setTls_termination(String tls_termination) {
		this.tls_termination = tls_termination;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
	
	

}
