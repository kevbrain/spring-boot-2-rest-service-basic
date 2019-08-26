package com.openshift.resources;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;


public class ConfigMap extends Resource{
	
	public static final String TYPE = "configmaps";
	
	public static final String API= "/api/v1/";
	
	public static final String PATHAPI = API + "namespaces/";
	
	private String name;
	
	private Map<String, String> data;

	public ConfigMap(String name) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;
	}

	public static Map<String, ConfigMap> getMapResources(InstanceOpenShift openshift,String projectName) {
		 HashMap<String, ConfigMap> cms = new HashMap<>();
		 try {			 	
			 	
			    JSONArray results = loadItemsResource(buildUrl(openshift,projectName,PATHAPI,API,TYPE), openshift);			
			
				for (int j=0;j<results.length();j++) {
					
					ConfigMap cm = extractConfigMap(results.getJSONObject(j));
					cms.put(cm.getName(),cm);
				}
				
			} catch (Exception e) {
				logger.log(Level.INFO,e.getMessage());
			}
		 return cms;
	}
	
	private static ConfigMap extractConfigMap(JSONObject extract) {
		
		JSONObject item= extract.getJSONObject("metadata");
		String name = item.getString("name").toString();	
		ConfigMap cm = new ConfigMap(name);
		
		try {
			JSONObject data = extract.getJSONObject("data");
			
			HashMap<String, String> datas = new HashMap<>();
			
			for (Object dt:data.keySet()) {
				datas.put(dt.toString(),data.get((String) dt).toString());
			}
			cm.setData(datas);
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		
		return cm;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	
	

}
