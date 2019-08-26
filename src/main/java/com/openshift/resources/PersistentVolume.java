package com.openshift.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class PersistentVolume extends Resource {
	
	public static final String TYPE = "services";
	
	public static final String API= "/api/v1/";
	
	public static final String PATHAPI = API;
	
	private String name;
	
	private String capacity;
	
	private String hostPath;
	
	private String[] accesMode;
	
	private String claimPolicy;
	

	public PersistentVolume(String name) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;
	}

	public static Map<String, PersistentVolume> getMapPV(InstanceOpenShift openshift) {
		
		String urlCall = openshift.getUrl()+"/api/v1/persistentvolumes";
		HashMap<String, PersistentVolume> pvs = new HashMap<>();
		try {
			HttpResponse<JsonNode> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
	
			JSONObject myObj = response_namespaces.getBody().getObject();
			
			
			JSONArray results = myObj.getJSONArray("items");						
			for (int i=0;i<results.length();i++) {
				JSONObject itemspv = results.getJSONObject(i).getJSONObject("metadata");
				String pvtName = itemspv.getString("name").toString();	
				PersistentVolume pv = new PersistentVolume(pvtName);
				pv.setCapacity(results.getJSONObject(i).getJSONObject("spec").getJSONObject("capacity").getString("storage"));
				pv.setHostPath(results.getJSONObject(i).getJSONObject("spec").getJSONObject("hostPath").getString("path"));
				pv.setClaimPolicy(results.getJSONObject(i).getJSONObject("spec").getString("persistentVolumeReclaimPolicy"));
				JSONArray accesMod  =results.getJSONObject(i).getJSONObject("spec").getJSONArray("accessModes");
				String[] accsModtab = new String[accesMod.length()];
				for (int j=0;j<accesMod.length();j++) {					
					accsModtab[j]=accesMod.getString(j);
				}
				pv.setAccesMode(accsModtab);
				pvs.put(pvtName, pv);
			}
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
			
		return pvs;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getHostPath() {
		return hostPath;
	}

	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}

	public String[] getAccesMode() {
		return accesMode;
	}

	public void setAccesMode(String[] accesMode) {
		this.accesMode = accesMode;
	}

	public String getClaimPolicy() {
		return claimPolicy;
	}

	public void setClaimPolicy(String claimPolicy) {
		this.claimPolicy = claimPolicy;
	}

	

}
