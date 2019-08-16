package com.openshift.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.openshift.compare.ConfigMapCompare;
import com.openshift.compare.ContainerCompare;
import com.openshift.compare.DCCompare;
import com.openshift.compare.PersistentVolumeCompare;
import com.openshift.compare.ProjectCompare;
import com.openshift.compare.RouteCompare;
import com.openshift.compare.SecretCompare;
import com.openshift.compare.ServiceCompare;
import com.openshift.resources.ConfigMap;
import com.openshift.resources.Containers;
import com.openshift.resources.DeploymentConfig;
import com.openshift.resources.Hostpath;
import com.openshift.resources.InstanceOpenShift;
import com.openshift.resources.ListenPort;
import com.openshift.resources.MyOCInstances;
import com.openshift.resources.OpenshiftProject;
import com.openshift.resources.PersistentVolume;
import com.openshift.resources.Routes;
import com.openshift.resources.Secret;
import com.openshift.resources.Services;
import com.openshift.resources.TriggerImageChange;
import com.openshift.resources.ValueFrom;
import com.openshift.resources.VolumeMount;
import com.openshift.resources.Volumes;
import com.openshift.resources.Resource;

public class OCService {
	
	// LOAD INSTANCES OC available
	
	public HashMap<String, InstanceOpenShift> retrieveAllInstances() {
		HashMap<String, InstanceOpenShift> myInstances =  new MyOCInstances().getMyOcs();
		for (InstanceOpenShift oc : myInstances.values()) {
			oc.setProjects(getMapProject(oc));
		}
		
		return myInstances;
	}
	
	
	// LOAD JSONString for export resources
	
	public String loadProject(String oc, String projectName) {
		
		String myObj =  null;
		InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
		String urlCall = instanceOpenShift.getUrl()+"/oapi/v1/projects/"+projectName;
		
		try {
			HttpResponse<String> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", instanceOpenShift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asString();
	
			 myObj = response_namespaces.getBody();
			 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cleanMetadata(myObj,projectName,null);
	}
		
	public String loadDC(String oc, String projectName, String dc, String namespace) {
		
		String myObj =  null;
		InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
		String urlCall = instanceOpenShift.getUrl()+"/oapi/v1/namespaces/"+projectName+"/deploymentconfigs/"+dc;
		
		try {
			HttpResponse<String> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", instanceOpenShift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asString();
	
			 myObj = response_namespaces.getBody();
			 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cleanMetadata(myObj,projectName,namespace);
	}
	
	public String loadService(String oc, String projectName, String service, String namespace) {
			
			String myObj =  null;
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			String urlCall = instanceOpenShift.getUrl()+"/api/v1/namespaces/"+projectName+"/services/"+service;
			
			try {
				HttpResponse<String> response_namespaces= Unirest.get(urlCall)
						  .header("Authorization", instanceOpenShift.getToken())
						  .header("cache-control", "no-cache")
						  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
						  .asString();
		
				 myObj = response_namespaces.getBody();
				 
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return cleanMetadata(myObj,projectName,namespace);
	}

	public String loadRoute(String oc, String projectName, String route, String namespace) {
	
		String myObj =  null;
		InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
		String urlCall = instanceOpenShift.getUrl()+"/oapi/v1/namespaces/"+projectName+"/routes/"+route;
		
		try {
			HttpResponse<String> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", instanceOpenShift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asString();
					  //.asJson();
	
			 myObj = response_namespaces.getBody();
			 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	return cleanMetadata(myObj,projectName,namespace);
	}

	public String loadSecret(String oc, String projectName, String secret, String namespace) {
	
	String myObj =  null;
	InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
	String urlCall = instanceOpenShift.getUrl()+"/api/v1/namespaces/"+projectName+"/secrets/"+secret;
	
	try {
		HttpResponse<String> response_namespaces= Unirest.get(urlCall)
				  .header("Authorization", instanceOpenShift.getToken())
				  .header("cache-control", "no-cache")
				  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
				  .asString();

		 myObj = response_namespaces.getBody();
		 
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return cleanMetadata(myObj,projectName,namespace);
}

	public String loadConfimap(String oc, String projectName, String configmap, String namespace) {
	
		String myObj =  null;
		InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
		String urlCall = instanceOpenShift.getUrl()+"/api/v1/namespaces/"+projectName+"/configmaps/"+configmap;
		
		try {
			HttpResponse<String> response_namespaces= Unirest.get(urlCall)
					  .header("Authorization", instanceOpenShift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asString();
	
			 myObj = response_namespaces.getBody();
			 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cleanMetadata(myObj,projectName,namespace);
	}

	public String loadPersitentVolume(String oc, String persitentVolume) {
	
	String myObj =  null;
	InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
	String urlCall = instanceOpenShift.getUrl()+"/api/v1/persistentvolumes/"+persitentVolume;
	
	try {
		HttpResponse<String> response_namespaces= Unirest.get(urlCall)
				  .header("Authorization", instanceOpenShift.getToken())
				  .header("cache-control", "no-cache")
				  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
				  .asString();

		 myObj = response_namespaces.getBody();
		 
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return cleanMetadata(myObj,null,null);
}

	
	
	// Get LIST Resources by OpenShift instance

	public List<OpenshiftProject> retrieveAllProjects(String oc) {
		List<OpenshiftProject> myprojects = new  ArrayList<>();	
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			if (instanceOpenShift!=null) {
				myprojects = new ArrayList<OpenshiftProject>(this.getMapProject(instanceOpenShift).values());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return myprojects;
		
	}
	
	public List<DeploymentConfig> retrieveDCFromProject(String oc,String projectName) {
		List<DeploymentConfig> mydcs = new  ArrayList<>();	
		InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
		if (instanceOpenShift!=null) {
			return new ArrayList<>(getMapDC(instanceOpenShift, projectName, null, null, null).values());
		}	
		return mydcs;
	}
	
	public List<Services> retrieveAllServices(String oc) {
		List<Services> myservices = new  ArrayList<>();	
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			if (instanceOpenShift!=null) {
				myservices = new ArrayList<Services>(this.getMapSVC(instanceOpenShift,null).values());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return myservices;
		
	}
	
	public List<Routes> retrieveAllRoutes(String oc) {
		List<Routes> myroutes = new  ArrayList<>();	
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			if (instanceOpenShift!=null) {
				myroutes = new ArrayList<Routes>(this.getMapRoutes(instanceOpenShift,null).values());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return myroutes;
	}
	
	public List<Secret> retrieveAllSecrets(String oc) {
		List<Secret> mySecrets = new ArrayList<>();
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			if (instanceOpenShift!=null) {
				mySecrets = new ArrayList<Secret>(this.getMapSecrets(instanceOpenShift,null).values());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return mySecrets;
	}
	
	public List<ConfigMap> retrieveAllConfigMap(String oc) {
		List<ConfigMap> cms = new ArrayList<>();
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			if (instanceOpenShift!=null) {
				cms = new ArrayList<ConfigMap>(this.getMapConfigMap(instanceOpenShift,null).values());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return cms;
	}
		
	
	
	
	// Extract Details of a resource

	public DeploymentConfig getDC(InstanceOpenShift openshift,String projectName,String dcName) {
		
		DeploymentConfig dc = null;
		try {
			
			String urldc = openshift.getUrl()+"/oapi/v1/namespaces/"+projectName+"/deploymentconfigs/"+dcName;
			HttpResponse<JsonNode> response_dcs= Unirest.get(urldc)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			JSONObject myObjdc = response_dcs.getBody().getObject();						
			dc = extartDC(myObjdc,null);
			
		} catch (Exception e) {
			
		}
		return dc;
	}
	
	private Services extractSVC(JSONObject extractSVC) {
			
		JSONObject itemsSVC = extractSVC.getJSONObject("metadata");
		String svcNane = itemsSVC.getString("name").toString();			
		Services svc = new Services(svcNane);
		
		String ipcluster = extractSVC.getJSONObject("spec").getString("clusterIP");
		String type =  extractSVC.getJSONObject("spec").getString("type");
		svc.setClusterIP(ipcluster);
		svc.setType(type);
		
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
			
		}
		if (selectorDC==null) {
			try {
				selectorDC = extractSVC.getJSONObject("spec").getJSONObject("selector").getString("name");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		svc.setSelectorDC(selectorDC);
		
		return svc;
	}
	
	private Routes extractRoute(JSONObject extract) {
		
		JSONObject itemsroute = extract.getJSONObject("metadata");
		String routeName = itemsroute.getString("name").toString();		
		String host = extract.getJSONObject("spec").getString("host");
		Routes route = new Routes(routeName,host);
		
		String service = extract.getJSONObject("spec").getJSONObject("to").getString("name");
		
		route.setService(service);
			
		return route;
	}
	
	private Secret extractSecret(JSONObject extract) {
		
		JSONObject item= extract.getJSONObject("metadata");
		String secretName = item.getString("name").toString();	
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
			// TODO: handle exception
		}
		return secret;
	}
	
	private ConfigMap extractConfigMap(JSONObject extract) {
		
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
			// TODO: handle exception
		}
		
		return cm;
	}
	
	private DeploymentConfig extartDC(JSONObject extractDC,HashMap<String, Secret> secrets) {
		
		JSONObject itemsDC = extractDC.getJSONObject("metadata");
		String DCNane = itemsDC.getString("name").toString();			
		DeploymentConfig dc = new DeploymentConfig(DCNane);
		
		
		// Deployment Config Containers

		HashMap<String, Containers>  containers= new HashMap<>();
		try {
						
			JSONArray resultscontainers = extractDC.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("containers");

			for (int k=0;k<resultscontainers.length();k++) {
				
					JSONObject itemsContainer = resultscontainers.getJSONObject(k);
					Containers cont = new Containers(itemsContainer.getString("name").toString());
				
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
						e.printStackTrace();
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
						// TODO: handle exception
					}
					
					containers.put(cont.getName(), cont);	
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		dc.setContainers(containers);
		
		// Deployment Config Volumes
		
		HashMap<String, Volumes> volumes = new HashMap<>();
		try {
			JSONArray resultsvolumes = extractDC.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("volumes");
					
			for (int k=0;k<resultsvolumes.length();k++) {
				JSONObject itemsVolume = resultsvolumes.getJSONObject(k);
				Volumes  vol = new Volumes(itemsVolume.getString("name").toString());
				
				// ConfigMap
				try {
					JSONObject configmap = itemsVolume.getJSONObject("configMap");
					ConfigMap cm =new ConfigMap(configmap.getString("name"));
					
					vol.setConfigMap(cm);					
					
				} catch (Exception e) {
					// TODO: handle exception
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
					// TODO: handle exception
				}
				
				// Hostpath
				try {
					JSONObject hostpath = itemsVolume.getJSONObject("hostPath");
					vol.setHostpath(new Hostpath(hostpath.getString("path"), hostpath.getString("type")));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				// Claim
				try {
					JSONObject claim = itemsVolume.getJSONObject("persistentVolumeClaim");
					vol.setClaim(new PersistentVolume(claim.getString("claimName")));
				} catch (Exception e) {
					// TODO: handle exception
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
			// TODO: handle exception
		}
		dc.setTriggerImageChange(triggerImageChange);
		return dc;
	}
		

	
	// Get MAP Resources by OpenShift instance and Project
	
	public HashMap<String, OpenshiftProject> getMapProject(InstanceOpenShift openshift) {
		
		String urlCall = openshift.getUrl()+"/api/v1/namespaces";
				
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
					
					HashMap<String, Services> svcs= getMapSVC(openshift, projectName);
					HashMap<String, Routes> routes= getMapRoutes(openshift, projectName);	
					HashMap<String, Secret> secrets = getMapSecrets(openshift, projectName);
					HashMap<String, ConfigMap> configMaps = getMapConfigMap(openshift, projectName);
					HashMap<String, DeploymentConfig> dcs = getMapDC(openshift,projectName,svcs,routes,secrets);
															
					project.setDeploymentConfigs(dcs);
					project.setServices(svcs);
					project.setRoutes(routes);
					project.setConfigMaps(configMaps);
												
					projects.put(project.getProjectName(),project);
				}
			}
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return projects;
		
	}
	
	public HashMap<String, PersistentVolume> getMapPV(InstanceOpenShift openshift) {
		
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
			e.printStackTrace();
		}
			
		return pvs;
	}
	
	public HashMap<String, DeploymentConfig> getMapDC(InstanceOpenShift openshift,String projectName,HashMap<String, Services> svcs,HashMap<String, Routes> routes,HashMap<String, Secret> secrets) {
		
		HashMap<String, DeploymentConfig> dcs = new HashMap<>();
		try {
			String urldc = openshift.getUrl()+"/oapi/v1/namespaces/"+projectName+"/deploymentconfigs";
			
			HttpResponse<JsonNode> response_dcs= Unirest.get(urldc)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			JSONObject myObjdc = response_dcs.getBody().getObject();
			JSONArray resultsdc = myObjdc.getJSONArray("items");
			
			
			
			for (int j=0;j<resultsdc.length();j++) {
					DeploymentConfig dc = extartDC(resultsdc.getJSONObject(j),secrets);
					dcs.put(dc.getName(),dc);
			}
						
			
			// Link services to DC and routes and secrets
			if (svcs!=null) {
				for(Services service:svcs.values()) {
					if (dcs.containsKey(service.getSelectorDC())) {						
						dcs.get(service.getSelectorDC()).setServicesLinked(service);;
					}
				}
			}
			if (routes!=null) {
				for(Routes route:routes.values()) {
					if (svcs.containsKey(route.getService())) {
						svcs.get(route.getService()).setRoute(route);
					}
				}
			}
			
			
			
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dcs;
		
	}
	
	public HashMap<String, Services> getMapSVC(InstanceOpenShift openshift,String projectName) {
		
		
		HashMap<String, Services> svcs = new HashMap<>();
		try {
			String suburl = projectName!=null?"namespaces/"+projectName+"/":"";
			String urlsvc = openshift.getUrl()+"/api/v1/"+suburl+"services";
						
			HttpResponse<JsonNode> response_svcs= Unirest.get(urlsvc)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			JSONObject myObjSvc = response_svcs.getBody().getObject();			
			JSONArray resultsSvc = myObjSvc.getJSONArray("items");
			
			for (int j=0;j<resultsSvc.length();j++) {
				
				Services svc = extractSVC(resultsSvc.getJSONObject(j));
				svcs.put(svc.getName(),svc);
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return svcs;
		
}
	
	public HashMap<String, Routes> getMapRoutes(InstanceOpenShift openshift,String projectName) {
		
		
		HashMap<String, Routes> routes = new HashMap<>();
		try {
			String suburl = projectName!=null?"namespaces/"+projectName+"/":"";
			String url = openshift.getUrl()+"/oapi/v1/"+suburl+"routes";
	
			HttpResponse<JsonNode> response_svcs= Unirest.get(url)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			JSONObject myObj = response_svcs.getBody().getObject();
			JSONArray resultsSvc = myObj.getJSONArray("items");
			
			for (int j=0;j<resultsSvc.length();j++) {
				
				Routes route = extractRoute(resultsSvc.getJSONObject(j));
				routes.put(route.getName(),route);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routes;
		
	}

	public HashMap<String, Secret> getMapSecrets(InstanceOpenShift openshift,String projectName) {
	HashMap<String, Secret> secrets = new HashMap<>();
	try {
		String suburl = projectName!=null?"namespaces/"+projectName+"/":"";
		String url = openshift.getUrl()+"/api/v1/"+suburl+"secrets";

		HttpResponse<JsonNode> response= Unirest.get(url)
				  .header("Authorization", openshift.getToken())
				  .header("cache-control", "no-cache")
				  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
				  .asJson();
		JSONObject myObj = response.getBody().getObject();
		JSONArray results = myObj.getJSONArray("items");
		
	
		for (int j=0;j<results.length();j++) {
			
			Secret secret = extractSecret(results.getJSONObject(j));
			secrets.put(secret.getName(),secret);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return secrets;
}

	public HashMap<String, ConfigMap> getMapConfigMap(InstanceOpenShift openshift,String projectName) {
	 HashMap<String, ConfigMap> cms = new HashMap<>();
	 try {
			String suburl = projectName!=null?"namespaces/"+projectName+"/":"";
			String url = openshift.getUrl()+"/api/v1/"+suburl+"configmaps";

			HttpResponse<JsonNode> response= Unirest.get(url)
					  .header("Authorization", openshift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			JSONObject myObj = response.getBody().getObject();
			JSONArray results = myObj.getJSONArray("items");
			
		
			for (int j=0;j<results.length();j++) {
				
				ConfigMap cm = extractConfigMap(results.getJSONObject(j));
				cms.put(cm.getName(),cm);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 return cms;
}
	

	// Get ARRAY Resources by OpenShift instance and project
	
	public ArrayList<Resource> getArrayResources(String oc,String resourcetype,String project) {
		ArrayList<Resource> resources = new ArrayList<>();
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			String typeapi="api";
			if ("deploymentconfigs".equalsIgnoreCase(resourcetype)
					|| "routes".equalsIgnoreCase(resourcetype)) {
				typeapi="oapi";
			}
			
			String url = instanceOpenShift.getUrl()+"/"+typeapi+"/v1/namespaces/"+project+"/"+resourcetype;
			
			System.out.println("getArrayResources url = "+url);
			HttpResponse<JsonNode> response_= Unirest.get(url)
					  .header("Authorization", instanceOpenShift.getToken())
					  .header("cache-control", "no-cache")
					  .header("Postman-Token", "795e481e-12b0-462d-ad54-91a7a01e6a7e")
					  .asJson();
			JSONObject myObjdc = response_.getBody().getObject();
			JSONArray results = myObjdc.getJSONArray("items");
						
			for (int j=0;j<results.length();j++) {
			
				JSONObject items = results.getJSONObject(j).getJSONObject("metadata");
				String name = items.getString("name").toString();	
				Resource resource = new Resource(name, resourcetype,"true");
				resources.add(resource);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resources;
	}
	
	
	// Comparator

	public HashMap<String,ProjectCompare> compareInstances( String ocA, String ocB) {
		
		HashMap<String,ProjectCompare> projectCompare = new HashMap<>();	
		InstanceOpenShift instanceOpenShiftA = new MyOCInstances().getMyOcs().get(ocA);
		InstanceOpenShift instanceOpenShiftB = new MyOCInstances().getMyOcs().get(ocB);
		
		//HashMap<String, OpenshiftProject> mapProjectsInstanceA = new HashMap<>();
		HashMap<String, OpenshiftProject> mapProjectsInstanceA = this.getMapProject(instanceOpenShiftA);
		//HashMap<String, OpenshiftProject> mapProjectsInstanceB = new HashMap<>();
		HashMap<String, OpenshiftProject> mapProjectsInstanceB = this.getMapProject(instanceOpenShiftB);
		
		HashMap<String, PersistentVolume> mapVolumesA = this.getMapPV(instanceOpenShiftA);
		HashMap<String, PersistentVolume> mapVolumesB = this.getMapPV(instanceOpenShiftB);
		
		HashMap<String, PersistentVolumeCompare> pvcompare = new HashMap<>();
		for (String keyPV:mapVolumesA.keySet()) {
			if (!pvcompare.containsKey(keyPV)) {
				pvcompare.put(keyPV, new PersistentVolumeCompare(mapVolumesA.get(keyPV), null));
			} else {
				pvcompare.get(keyPV).setPersistentVolumeA(mapVolumesA.get(keyPV));
			}
		}
		for (String keyPV:mapVolumesB.keySet()) {
			if (!pvcompare.containsKey(keyPV)) {
				pvcompare.put(keyPV, new PersistentVolumeCompare(null,mapVolumesB.get(keyPV)));
			} else {
				pvcompare.get(keyPV).setPersistentVolumeB(mapVolumesB.get(keyPV));
			}
		}
		
		
				
		for (String keyProject:mapProjectsInstanceA.keySet()) {
			if (!projectCompare.containsKey(keyProject)) {
				projectCompare.put(keyProject, new ProjectCompare(mapProjectsInstanceA.get(keyProject), null));
			} else {
				projectCompare.get(keyProject).setProjectAName(keyProject);
				projectCompare.get(keyProject).setProjectA(mapProjectsInstanceA.get(keyProject));
			}
		}
		for (String keyProject:mapProjectsInstanceB.keySet()) {
			if (!projectCompare.containsKey(keyProject)) {
				projectCompare.put(keyProject, new ProjectCompare(null,mapProjectsInstanceB.get(keyProject)));
			} else {
				projectCompare.get(keyProject).setProjectBName(keyProject);
				projectCompare.get(keyProject).setProjectB(mapProjectsInstanceB.get(keyProject));
			}
		}
		
		for (String projectName:projectCompare.keySet()) {
			projectCompare.get(projectName).setPersistentVolumeCompare(pvcompare);
			projectCompare.get(projectName).setDcCompare(compareDistinctProjects(ocA, ocB, projectName,projectName));
			
			/*
			HashMap<String, Services> svcsA= new HashMap<>();
			HashMap<String, Routes> routesA= new HashMap<>();
			HashMap<String, Secret> secretsA= new HashMap<>();
			HashMap<String, ConfigMap> cmA = new HashMap<>();
			*/
			
			
			HashMap<String, Services> svcsA= getMapSVC(instanceOpenShiftA, projectName);
			HashMap<String, Routes> routesA= getMapRoutes(instanceOpenShiftA, projectName);
			HashMap<String, Secret> secretsA= getMapSecrets(instanceOpenShiftA, projectName);
			HashMap<String, ConfigMap> cmA = getMapConfigMap(instanceOpenShiftA, projectName);
			
			
			HashMap<String, Services> svcsB= new HashMap<>();
			HashMap<String, Routes> routesB= new HashMap<>();
			HashMap<String, Secret> secretsB= new HashMap<>();
			HashMap<String, ConfigMap> cmB = new HashMap<>();
			
			
			if ( !(projectName.equalsIgnoreCase("kafka") )) {
				 svcsB= getMapSVC(instanceOpenShiftB, projectName);
				 routesB= getMapRoutes(instanceOpenShiftB, projectName);
				 secretsB= getMapSecrets(instanceOpenShiftB, projectName);
				 cmB = getMapConfigMap(instanceOpenShiftB, projectName);
			} 
			
			// Services Compare
			HashMap<String,ServiceCompare> serviceCompare = new HashMap<>();
			for (String keyservice:svcsA.keySet()) {
				if (!serviceCompare.containsKey(keyservice)) {
					serviceCompare.put(keyservice, new ServiceCompare(svcsA.get(keyservice), null));
				} else {
					serviceCompare.get(keyservice).setServiceA(svcsA.get(keyservice));
				}
			}
			for (String keyservice:svcsB.keySet()) {
				if (!serviceCompare.containsKey(keyservice)) {
					serviceCompare.put(keyservice, new ServiceCompare(null,svcsB.get(keyservice)));
				} else {
					serviceCompare.get(keyservice).setServiceB(svcsB.get(keyservice));
				}
			}
			projectCompare.get(projectName).setServiceCompare(serviceCompare);
			
			// Routes Compare
			HashMap<String, RouteCompare> routeCompare = new HashMap<>();
			for (String keyRoute:routesA.keySet()) {				
				if (!routeCompare.containsKey(keyRoute)) {
					routeCompare.put(keyRoute, new RouteCompare(routesA.get(keyRoute),null));
				} else {
					routeCompare.get(keyRoute).setRouteA(routesA.get(keyRoute));
				}
			}
			for (String keyRoute:routesB.keySet()) {
				if (!routeCompare.containsKey(keyRoute)) {
					routeCompare.put(keyRoute, new RouteCompare(null,routesB.get(keyRoute)));
				} else {
					routeCompare.get(keyRoute).setRouteB(routesB.get(keyRoute));
				}
			}
			
			for (RouteCompare rtcomp: routeCompare.values()) {
				rtcomp.compare();
			}
			
			projectCompare.get(projectName).setRouteCompare(routeCompare);
			
			// Secrets Compare
			HashMap<String,SecretCompare> secretCompare = new  HashMap<>();
			for (String keySecret:secretsA.keySet()) {
				if (!secretCompare.containsKey(keySecret)) {
					secretCompare.put(keySecret, new SecretCompare(secretsA.get(keySecret), null));
				} else {
					secretCompare.get(keySecret).setSecretA(secretsA.get(keySecret));
				}
			}
			for (String keySecret:secretsB.keySet()) {
				if (!secretCompare.containsKey(keySecret)) {
					secretCompare.put(keySecret, new SecretCompare(null,secretsB.get(keySecret)));
				} else {
					secretCompare.get(keySecret).setSecretB(secretsB.get(keySecret));
				}
			}
			
			for(SecretCompare sect : secretCompare.values()) {
				sect.compare();
			}
			
			projectCompare.get(projectName).setSecretCompare(secretCompare);
			
			
			// ConfigMap Compare
			HashMap<String,ConfigMapCompare> cmCompare = new HashMap<>();
			for (String keycm:cmA.keySet()) {
				if (!cmCompare.containsKey(keycm)) {
					cmCompare.put(keycm, new ConfigMapCompare(cmA.get(keycm), null));
				} else {
					cmCompare.get(keycm).setConfigMapA(cmA.get(keycm));
				}
			}
			for (String keycm:cmB.keySet()) {
				if (!cmCompare.containsKey(keycm)) {
					cmCompare.put(keycm, new ConfigMapCompare(null,cmB.get(keycm)));
				} else {
					cmCompare.get(keycm).setConfigMapB(cmB.get(keycm));
				}
			}
			
			for (ConfigMapCompare mpc:cmCompare.values()) {
				mpc.compare();
			}
			projectCompare.get(projectName).setConfigMapCompare(cmCompare);
		}

		return projectCompare;
	}
	
	public DCCompare compareDistinctDC(String ocA, String ocB ,String projectNameA ,String projectNameB, String dcNameA, String dcNameB) {
		System.out.println("**** compareDistinctDC *****");
		DCCompare dcCompare = null;
		try {
			InstanceOpenShift instanceOpenShiftA = new MyOCInstances().getMyOcs().get(ocA);
			InstanceOpenShift instanceOpenShiftB = new MyOCInstances().getMyOcs().get(ocB);
			DeploymentConfig dcA = getDC(instanceOpenShiftA, projectNameA, dcNameA);
			DeploymentConfig dcB = getDC(instanceOpenShiftB, projectNameB, dcNameB);
			
			HashMap<String, Services> svcsA= getMapSVC(instanceOpenShiftA, projectNameA);
			HashMap<String, Routes> routesA= getMapRoutes(instanceOpenShiftA, projectNameA);
			HashMap<String, Secret> secretsA= getMapSecrets(instanceOpenShiftA, projectNameA);
			
			HashMap<String, Services> svcsB= getMapSVC(instanceOpenShiftB, projectNameB);
			HashMap<String, Routes> routesB= getMapRoutes(instanceOpenShiftB, projectNameB);
			HashMap<String, Secret> secretsB= getMapSecrets(instanceOpenShiftB, projectNameB);
			
			dcCompare =new DCCompare(dcA, dcB);
			dcCompare.generateAllCompare();		
			for (ContainerCompare ct:dcCompare.getContainerCompare().values()) {
				ct.generateAllCompare();
			}
		} catch (Exception e) {
			
		}
		return dcCompare;
	}
	
	public HashMap<String,DCCompare> compareDistinctProjects( String ocA, String ocB ,String projectNameA ,String projectNameB) {
		
		System.out.println("***> compareDistinctProjects");
		
		HashMap<String,DCCompare> dcCompare = new HashMap<>();
		
		InstanceOpenShift instanceOpenShiftA = new MyOCInstances().getMyOcs().get(ocA);
		InstanceOpenShift instanceOpenShiftB = new MyOCInstances().getMyOcs().get(ocB);
		
		/*
		HashMap<String, Services> svcsA= new HashMap<>();
		HashMap<String, Routes> routesA= new HashMap<>();
		HashMap<String, Secret> secretsA= new HashMap<>();
		*/
		
		HashMap<String, Services> svcsA= getMapSVC(instanceOpenShiftA, projectNameA);
		HashMap<String, Routes> routesA= getMapRoutes(instanceOpenShiftA, projectNameA);
		HashMap<String, Secret> secretsA= getMapSecrets(instanceOpenShiftA, projectNameA);
		
		
		HashMap<String, Services> svcsB= getMapSVC(instanceOpenShiftB, projectNameB);		
		HashMap<String, Routes> routesB= getMapRoutes(instanceOpenShiftB, projectNameB);
		HashMap<String, Secret> secretsB= getMapSecrets(instanceOpenShiftB, projectNameB);
		
		/*
		HashMap<String, Services> svcsB= new HashMap<>();
		HashMap<String, Routes> routesB= new HashMap<>();
		HashMap<String, Secret> secretsB= new HashMap<>();
		*/
		//HashMap<String, DeploymentConfig> mapdcA = new HashMap<>();
		HashMap<String, DeploymentConfig> mapdcA = this.getMapDC(instanceOpenShiftA, projectNameA,svcsA,routesA,secretsA);
		//HashMap<String, DeploymentConfig> mapdcB = new HashMap<>();
		HashMap<String, DeploymentConfig> mapdcB = this.getMapDC(instanceOpenShiftB, projectNameB,svcsB,routesB,secretsB);
		
		for (String keyDc:mapdcA.keySet()) {
			if (!dcCompare.containsKey(keyDc)) {
				dcCompare.put(keyDc, new DCCompare(mapdcA.get(keyDc), null));
			} else {
				dcCompare.get(keyDc).setDcAName("<ins style=\"background:#e6ffe6;\">"+keyDc+"</ins>");
				dcCompare.get(keyDc).setDcA(mapdcA.get(keyDc));
			}
		}
		for (String keyDc:mapdcB.keySet()) {
			if (!dcCompare.containsKey(keyDc)) {
				dcCompare.put(keyDc, new DCCompare(null, mapdcB.get(keyDc)));
			} else {
				dcCompare.get(keyDc).setDcBName(keyDc);
				dcCompare.get(keyDc).setDcB(mapdcB.get(keyDc));
			}
		}
		
		for (DCCompare dcComp:dcCompare.values()) {
			dcComp.generateAllCompare();
			for (ContainerCompare ct:dcComp.getContainerCompare().values()) {
				ct.generateAllCompare();
			}
		}
		
		
		
		
		return dcCompare;
	}
	
	
	// Clean METADATA config Resource
	
	public String cleanMetadata(String myObj,String oldnamespace,String newnamespace) {
		
		myObj =  myObj.replace("resourceVersion", "_resourceVersion");
		if (newnamespace!=null && oldnamespace!=null) {
			myObj = myObj.replace(oldnamespace, newnamespace);
		}
		
		return myObj;
	}
	
}
