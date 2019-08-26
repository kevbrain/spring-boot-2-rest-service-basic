package com.openshift.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.openshift.services.OCService;

public class Resource {

	private String pathAPi;
	
	private String resourceKey;
	
	private String resourceType;
	
	private String selected;
	
	protected static Logger logger = Logger.getLogger(OCService.class.getName());

	public Resource(String resourceKey, String resourceType,String selected, String pathAPi) {
		super();
		this.resourceKey = resourceKey;
		this.resourceType = resourceType;
		this.selected = selected;
		this.pathAPi = pathAPi;
	}
	
	public static String buildUrl(InstanceOpenShift openshift,String projectName,String PATHAPI,String API,String TYPE) {
		String url = openshift.getUrl();
	 	if (projectName!=null && !projectName.isEmpty()) {
	 		url = url + PATHAPI + projectName;
	 	} else {
	 		url = url + API ;
	 	}
	 	url = url +"/" +TYPE;
	 	
	 	System.out.println("url = "+url);
	 	return url;
	}

	public String loadResource(String oc, String projectName, String resourceKey, String namespace) {
					
		String myObj =  null;
		String urlCall = null;
		InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
		
		if (projectName!=null && !projectName.isEmpty()) {
			 urlCall = instanceOpenShift.getUrl()+pathAPi+projectName+"/"+resourceType+"/"+resourceKey;
		} else {
			 urlCall = instanceOpenShift.getUrl()+pathAPi+"/"+resourceType+"/"+projectName;
		}
		
		try {
			HttpResponse<String> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", instanceOpenShift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asString();
	
			 myObj = response_namespaces.getBody();
			 
			
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return cleanMetadata(myObj,projectName,namespace);
	}
	
	
	
	public static JSONArray loadItemsResource(String url,InstanceOpenShift openshift) {
		
			
		HttpResponse<JsonNode> response;
		
		try {
			response = Unirest.get(url)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			return response.getBody().getObject().getJSONArray("items");
		
		} catch (UnirestException e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return null;
	}
	
	// Clean METADATA config Resource
	
	public String cleanMetadata(String myObj,String oldnamespace,String newnamespace) {
		
		myObj =  myObj.replace("resourceVersion", "_resourceVersion");
		if (newnamespace!=null && oldnamespace!=null) {
			myObj = myObj.replace(oldnamespace, newnamespace);
		}
		
		return myObj;
	}
	
	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	
}
