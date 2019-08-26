package com.openshift.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

public class Secret extends Resource {

	public static final String TYPE = "secrets";
	
	public static final String API= "/api/v1/";
	
	public static final String PATHAPI = API+"namespaces/";
	
	private String name;
	
	private String type;
	
	private HashMap<String, String> data;
	

	public Secret(String name) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;
	}
	
	public static Map<String, Secret> getMapSecrets(InstanceOpenShift openshift,String projectName) {
		
		HashMap<String, Secret> secrets = new HashMap<>();
		 try {			 	
			 	
			    JSONArray results = loadItemsResource(buildUrl(openshift,projectName,PATHAPI,API,TYPE), openshift);			
			
				for (int j=0;j<results.length();j++) {
					
					Secret secret = extractSecret(results.getJSONObject(j));
					secrets.put(secret.getName(),secret);
				}
				
			} catch (Exception e) {
				logger.log(Level.INFO,e.getMessage());
			}
		
		return secrets;
	}
	
	private static Secret extractSecret(JSONObject extract) {
		
		JSONObject item= extract.getJSONObject("metadata");
		String secretName = item.getString("name");	
		Secret secret = new Secret(secretName);
		
		JSONObject data = extract.getJSONObject("data");
		
		HashMap<String, String> datas = new HashMap<>();
		
		for (Object dt:data.keySet()) {
			datas.put(dt.toString(),data.get((String) dt).toString());
		}
		secret.setData(datas);
		
		try {
			String type = extract.getString("type");
			secret.setType(type);
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
	
	
	
	
}
