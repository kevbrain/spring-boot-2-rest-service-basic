package com.openshift.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OpenshiftProject extends Resource{

	public static final String TYPE = "projects";
	
	public static final String API= "/oapi/v1/";
	
	public static final String PATHAPI = API;
	
	private String projectName;
	
	private Map<String, DeploymentConfig> deploymentConfigs;
	
	private Map<String, Services> services;
	
	private Map<String, Routes> routes;
	
	private Map<String, ConfigMap> configMaps;
	
	

	public OpenshiftProject(String projectName) {
		super(projectName,TYPE,"true",PATHAPI);
		this.projectName = projectName;
		this.deploymentConfigs = new HashMap<>();
		this.services = new HashMap<>();
		this.routes = new HashMap<>();
	}
	
	public static Map<String, OpenshiftProject> getMapProject(InstanceOpenShift openshift) {
		
		System.out.println("GET MAP PROJECT ....");
		String urlCall = openshift.getUrl()+"/api/v1/namespaces";
		System.out.println("URL = "+urlCall);
				
	    HashMap<String, OpenshiftProject> projects = new HashMap<>();
		
		try {
			HttpResponse<JsonNode> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
	
			JSONObject myObj = response_namespaces.getBody().getObject();
			
			// load dcs , services , routes , secrets
			JSONArray results = myObj.getJSONArray("items");						
			for (int i=0;i<results.length();i++) {
				
				JSONObject itemsProject = results.getJSONObject(i).getJSONObject("metadata");
				String projectName = itemsProject.getString("name").toString();	
				
				if ( !(projectName.equalsIgnoreCase("kafka") && openshift.getName().equalsIgnoreCase("PRODUCTION") )) {
					OpenshiftProject project =new OpenshiftProject(projectName);	
					
					Map<String, Services> svcs= Services.getMapSVC(openshift, projectName);
					Map<String, Routes> routes= Routes.getMapRoutes(openshift, projectName);//getMapRoutes(openshift, projectName);	
					Map<String, Secret> secrets = Secret.getMapSecrets(openshift, projectName); //getMapSecrets(openshift, projectName);
					Map<String, ConfigMap> configMaps = ConfigMap.getMapResources(openshift, projectName); //getMapConfigMap(openshift, projectName);
					Map<String, DeploymentConfig> dcs = DeploymentConfig.getMapDC(openshift, projectName, svcs, routes, secrets);//getMapDC(openshift,projectName,svcs,routes,secrets);
															
					project.setDeploymentConfigs(dcs);
					project.setServices(svcs);
					project.setRoutes(routes);
					project.setConfigMaps(configMaps);
												
					projects.put(project.getProjectName(),project);
				}
			}
		} catch (UnirestException e) {
			logger.log(Level.INFO,e.getMessage());
		}		
		return projects;
		
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Map<String, DeploymentConfig> getDeploymentConfigs() {
		return deploymentConfigs;
	}

	public void setDeploymentConfigs(Map<String, DeploymentConfig> deploymentConfigs) {
		this.deploymentConfigs = deploymentConfigs;
	}

	public Map<String, Services> getServices() {
		return services;
	}

	public void setServices(Map<String, Services> services) {
		this.services = services;
	}

	public Map<String, Routes> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, Routes> routes) {
		this.routes = routes;
	}

	public Map<String, ConfigMap> getConfigMaps() {
		return configMaps;
	}

	public void setConfigMaps(Map<String, ConfigMap> configMaps) {
		this.configMaps = configMaps;
	}

	
	
	
}
