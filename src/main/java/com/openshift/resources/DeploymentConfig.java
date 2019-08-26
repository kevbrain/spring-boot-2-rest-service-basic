package com.openshift.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;



public class DeploymentConfig extends Resource {
	
	public static final String TYPE = "deploymentconfigs";
	
	public static final String API= "/oapi/v1/";
	
	public static final String PATHAPI = API+"namespaces/";

	private String name;
	
	private Map<String, Containers> containers;
	
	private Map<String, Volumes> volumes;
	
	private Map<String, TriggerImageChange> triggerImageChange;
	
	private Services servicesLinked;
	
	public DeploymentConfig(String name) {
		super(name,TYPE,"true",PATHAPI);
		this.name = name;
		this.containers = new HashMap<>();
		this.volumes = new HashMap<>();
		this.triggerImageChange = new HashMap<>();
		
	}
	
	public static Map<String, DeploymentConfig> getMapDC(InstanceOpenShift openshift,String projectName,Map<String, Services> svcs,Map<String, Routes> routes,Map<String, Secret> secrets) {
		
		HashMap<String, DeploymentConfig> dcs = new HashMap<>();
		try {
			 JSONArray results = loadItemsResource(buildUrl(openshift,projectName,PATHAPI,API,TYPE), openshift);	
	
			
			
			for (int j=0;j<results.length();j++) {
					DeploymentConfig dc = extartDC(results.getJSONObject(j),secrets);
					dcs.put(dc.getName(),dc);
			}
						
			
			// Link services to DC and routes and secrets
			if (svcs!=null) {
				for(Services service:svcs.values()) {
					if (dcs.containsKey(service.getSelectorDC())) {						
						dcs.get(service.getSelectorDC()).setServicesLinked(service);
					}
				}
				if (routes!=null) {
					for(Routes route:routes.values()) {
						if (svcs.containsKey(route.getService())) {
							svcs.get(route.getService()).setRoute(route);
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return dcs;
		
	}
	
	public static DeploymentConfig extartDC(JSONObject extractDC,Map<String, Secret> secrets) {
		
		JSONObject itemsDC = extractDC.getJSONObject("metadata");
		String DCNane = itemsDC.getString("name").toString();			
		DeploymentConfig dc = new DeploymentConfig(DCNane);
		
		
		// Deployment Config Containers

		HashMap<String, Containers>  containers= new HashMap<>();
		try {
						
			JSONArray resultscontainers = extractDC.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("containers");

			for (int k=0;k<resultscontainers.length();k++) {
				
					JSONObject itemsContainer = resultscontainers.getJSONObject(k);
					Containers cont = new Containers(itemsContainer.getString("name"));
				
					// Container environment variables
					try {
						JSONArray resultsenv = itemsContainer.optJSONArray("env");
						HashMap<String, Object> envs = new HashMap<>();
						if (resultsenv!=null) {
							for (int l=0;l<resultsenv.length();l++) {
								
									if (resultsenv.getJSONObject(l).opt("value")!=null)		
										envs.put(resultsenv.getJSONObject(l).getString("name"), resultsenv.getJSONObject(l).get("value"));
									else if (resultsenv.getJSONObject(l).opt("valueFrom")!=null && resultsenv.getJSONObject(l).getJSONObject("valueFrom").opt("secretKeyRef")!=null)	{
										ValueFrom valueFrom = new ValueFrom(
												"secret",
												resultsenv.getJSONObject(l).getJSONObject("valueFrom").getJSONObject("secretKeyRef").getString("name"),
												resultsenv.getJSONObject(l).getJSONObject("valueFrom").getJSONObject("secretKeyRef").getString("key"));
										envs.put(resultsenv.getJSONObject(l).getString("name"),valueFrom);									
									}
								
							}
						}
						cont.setEnv(envs);
					} catch (Exception e) {
						logger.log(Level.INFO,e.getMessage());
					}
					
					// Container volumes mounts
					try {
						JSONArray resultsVolMount = resultscontainers.getJSONObject(k).getJSONArray("volumeMounts");
						HashMap<String, VolumeMount> volMounts = new HashMap<>();
						for (int l=0;l<resultsVolMount.length();l++) {
							volMounts.put(resultsVolMount.getJSONObject(l).getString("name"), new VolumeMount(resultsVolMount.getJSONObject(l).getString("name"), resultsVolMount.getJSONObject(l).getString("mountPath")));
						}
						cont.setVolmount(volMounts);
					} catch (Exception e) {
						logger.log(Level.INFO,e.getMessage());
					}
					
					containers.put(cont.getName(), cont);	
				
			}
			
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		dc.setContainers(containers);
		
		// Deployment Config Volumes
		
		HashMap<String, Volumes> volumes = new HashMap<>();
		try {
			JSONArray resultsvolumes = extractDC.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("volumes");
					
			for (int k=0;k<resultsvolumes.length();k++) {
				JSONObject itemsVolume = resultsvolumes.getJSONObject(k);
				Volumes  vol = new Volumes(itemsVolume.getString("name"));
				
				// ConfigMap
				try {
					JSONObject configmap = itemsVolume.getJSONObject("configMap");
					ConfigMap cm =new ConfigMap(configmap.getString("name"));
					
					vol.setConfigMap(cm);					
					
				} catch (Exception e) {
					logger.log(Level.INFO,e.getMessage());
				}
				
				
				// Secrets
				try {
					JSONObject secret = itemsVolume.getJSONObject("secret");
					Secret secretItem =new Secret(secret.getString("secretName"));
					
					if (secrets.containsKey(secretItem.getName())) {
						vol.setSecret(secrets.get(secretItem.getName()));
					} else {
						vol.setSecret(secretItem);
					}
					
					
				} catch (Exception e) {
					logger.log(Level.INFO,e.getMessage());
				}
				
				// Hostpath
				try {
					JSONObject hostpath = itemsVolume.getJSONObject("hostPath");
					vol.setHostpath(new Hostpath(hostpath.getString("path"), hostpath.getString("type")));
				} catch (Exception e) {
					logger.log(Level.INFO,e.getMessage());
				}
				
				// Claim
				try {
					JSONObject claim = itemsVolume.getJSONObject("persistentVolumeClaim");
					vol.setClaim(new PersistentVolume(claim.getString("claimName")));
				} catch (Exception e) {
					logger.log(Level.INFO,e.getMessage());
				}
				volumes.put(vol.getName(), vol);
			}
		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		dc.setVolumes(volumes);
		
		// Load Trigger
		HashMap<String, TriggerImageChange> triggerImageChange = new HashMap<>();
		try {
			JSONArray resultstriggers = extractDC.getJSONObject("spec").getJSONArray("triggers");
			
			for (int k=0;k<resultstriggers.length();k++) {
				JSONObject itemsTrigger = resultstriggers.getJSONObject(k);
				if (itemsTrigger.getString("type").equalsIgnoreCase("ImageChange")) {
					String namespace = itemsTrigger.getJSONObject("imageChangeParams").getJSONObject("from").getString("namespace");
					String imageWithTag = itemsTrigger.getJSONObject("imageChangeParams").getJSONObject("from").getString("name");;
					String image = imageWithTag.split(":")[0];
					String tag = imageWithTag.split(":")[1];
					String fullname = namespace+"/"+image;
					
					triggerImageChange.put(fullname, new  TriggerImageChange(namespace, image, tag));
					
				}
				
			}
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		dc.setTriggerImageChange(triggerImageChange);
		return dc;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Map<String, Containers> getContainers() {
		return containers;
	}

	public void setContainers(Map<String, Containers> containers) {
		this.containers = containers;
	}

	public Map<String, Volumes> getVolumes() {
		return volumes;
	}

	public void setVolumes(Map<String, Volumes> volumes) {
		this.volumes = volumes;
	}

	public Map<String, TriggerImageChange> getTriggerImageChange() {
		return triggerImageChange;
	}

	public void setTriggerImageChange(Map<String, TriggerImageChange> triggerImageChange) {
		this.triggerImageChange = triggerImageChange;
	}

	public void setTriggerImageChange(HashMap<String, TriggerImageChange> triggerImageChange) {
		this.triggerImageChange = triggerImageChange;
	}

	public Services getServicesLinked() {
		return servicesLinked;
	}

	public void setServicesLinked(Services servicesLinked) {
		this.servicesLinked = servicesLinked;
	}

	
	
	
	
}
