package com.openshift.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

public class Services extends Resource {

	public static final String TYPE = "services";
	
	public static final String API= "/api/v1/";
	
	public static final String PATHAPI = API+"namespaces/";
	
	private String name;
	
	private HashMap<String,ListenPort> listenPorts;
	
	private String typeService;
	
	private String clusterIP;
	
	private String selectorDC;
	
	private Routes route;
	
	
	public Services(String name) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;
	}
	
	public static Map<String, Services> getMapSVC(InstanceOpenShift openshift,String projectName) {
		
		HashMap<String, Services> services = new HashMap<>();
		 try {			 	
			 	
			 	
			    JSONArray results = loadItemsResource(buildUrl(openshift,projectName,PATHAPI,API,TYPE), openshift);			
			
				for (int j=0;j<results.length();j++) {
					
					Services service = extractSVC(results.getJSONObject(j));
					services.put(service.getName(),service);
				}
				
			} catch (Exception e) {
				logger.log(Level.INFO,e.getMessage());
			}
		
		return services;
	}

	private static Services extractSVC(JSONObject extractSVC) {
		
		JSONObject itemsSVC = extractSVC.getJSONObject("metadata");
		String svcNane = itemsSVC.getString("name");			
		Services svc = new Services(svcNane);
		
		String ipcluster = extractSVC.getJSONObject("spec").getString("clusterIP");
		String type =  extractSVC.getJSONObject("spec").getString("type");
		svc.setClusterIP(ipcluster);
		svc.setTypeService(type);
		
		HashMap<String,ListenPort> portsMap =new HashMap<>();
		JSONArray ports = extractSVC.getJSONObject("spec").getJSONArray("ports");
		
		for (int k=0;k<ports.length();k++) {
			JSONObject itemPort = ports.getJSONObject(k);
			String name= itemPort.getString("name");
			String protocol = itemPort.getString("protocol");
			int port = itemPort.getInt("port");
			portsMap.put(name,new ListenPort(name, protocol, port));
		}
		svc.setListenPorts(portsMap);
		String selectorDC = null;
		try {
			selectorDC = extractSVC.getJSONObject("spec").getJSONObject("selector").getString("deploymentconfig");
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		if (selectorDC==null) {
			try {
				selectorDC = extractSVC.getJSONObject("spec").getJSONObject("selector").getString("name");
			} catch (Exception e) {
				logger.log(Level.INFO,e.getMessage());
			}
		}
		svc.setSelectorDC(selectorDC);
		
		return svc;
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

	

	public String getTypeService() {
		return typeService;
	}

	public void setTypeService(String typeService) {
		this.typeService = typeService;
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
		return "Services [name=" + name + ", listenPorts=" + listenPorts + ", type=" + typeService + ", clusterIP=" + clusterIP
				+ "]";
	}
	
	
	
	
}

