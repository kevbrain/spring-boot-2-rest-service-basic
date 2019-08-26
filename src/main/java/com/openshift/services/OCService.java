package com.openshift.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.openshift.compare.ConfigMapCompare;
import com.openshift.compare.ContainerCompare;
import com.openshift.compare.DCCompare;
import com.openshift.compare.PersistentVolumeCompare;
import com.openshift.compare.ProjectCompare;
import com.openshift.compare.RouteCompare;
import com.openshift.compare.SecretCompare;
import com.openshift.compare.ServiceCompare;
import com.openshift.resources.ConfigMap;
import com.openshift.resources.DeploymentConfig;
import com.openshift.resources.InstanceOpenShift;
import com.openshift.resources.MyOCInstances;
import com.openshift.resources.OpenshiftProject;
import com.openshift.resources.PersistentVolume;
import com.openshift.resources.Resource;
import com.openshift.resources.Routes;
import com.openshift.resources.Secret;
import com.openshift.resources.Services;

public class OCService {
	
	Logger logger = Logger.getLogger(OCService.class.getName());
	
	// LOAD INSTANCES OC available
	
	public Map<String, InstanceOpenShift> retrieveAllInstances() {
		HashMap<String, InstanceOpenShift> myInstances =  new MyOCInstances().getMyOcs();
		for (InstanceOpenShift oc : myInstances.values()) {
			oc.setProjects(OpenshiftProject.getMapProject(oc));//getMapProject(oc));
		}
		
		return myInstances;
	}
	
	
	public String loadProject(String oc, String projectName) {
		
		// loadResource(String oc, String projectName, String resourceKey, String namespace)
		return new OpenshiftProject(projectName).loadResource(oc, projectName, OpenshiftProject.TYPE, "");
	}
	
	public String loadPersitentVolume(String oc, String persitentVolume) {
		
		return new PersistentVolume(persitentVolume).loadResource(oc, persitentVolume,PersistentVolume.TYPE, "");
	}
	
	public String loadDC(String oc, String projectName, String resourceKey, String namespace) {
		
		return new DeploymentConfig(resourceKey).loadResource(oc, projectName, resourceKey, namespace);
		
	}
	
	public String loadService(String oc, String projectName, String resourceKey, String namespace) {

		return new Services(resourceKey).loadResource(oc, projectName, resourceKey, namespace);
	}
	
	public String loadRoute(String oc, String projectName, String resourceKey, String namespace) {

		return new Routes(resourceKey).loadResource(oc, projectName, resourceKey, namespace);
	}
	
	public String loadSecret(String oc, String projectName, String resourceKey, String namespace) {

		return new Secret(resourceKey).loadResource(oc, projectName, resourceKey, namespace);
	}
	
	public String loadConfimap(String oc, String projectName, String resourceKey, String namespace) {

		return new ConfigMap(resourceKey).loadResource(oc, projectName, resourceKey, namespace);
	}
	
	
	// Get LIST Resources by OpenShift instance

	public List<OpenshiftProject> retrieveAllProjects(String oc) {
		
		return new ArrayList<>(OpenshiftProject.getMapProject(getOpenShiftInstance(oc)).values());
	
	}
	
	public List<DeploymentConfig> retrieveDCFromProject(String oc,String projectName) {
		
		return new ArrayList<>(DeploymentConfig.getMapDC(getOpenShiftInstance(oc), projectName, null, null, null).values());
		
	}
	
	public List<Services> retrieveAllServices(String oc) {
		
		return new ArrayList<>(Services.getMapSVC(getOpenShiftInstance(oc),null).values());
	
	}
	
	public List<Routes> retrieveAllRoutes(String oc) {
		
		return new ArrayList<>(Routes.getMapRoutes(getOpenShiftInstance(oc), null).values());
		
	}
	
	public InstanceOpenShift getOpenShiftInstance(String oc) {
		return new MyOCInstances().getMyOcs().get(oc);
	}
	
	public List<Secret> retrieveAllSecrets(String oc) {
		
		return new ArrayList<>(Secret.getMapSecrets(getOpenShiftInstance(oc), null).values());
				
	}
	
	public List<ConfigMap> retrieveAllConfigMap(String oc) {
			
		return new ArrayList<>(ConfigMap.getMapResources(getOpenShiftInstance(oc), null).values());
		
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
			dc = DeploymentConfig.extartDC(myObjdc,null);
			
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return dc;
	}
	


	// Get ARRAY Resources by OpenShift instance and project
	
	public List<Resource> getArrayResources(String oc,String resourcetype,String project) {
		ArrayList<Resource> resources = new ArrayList<>();
		try {
			InstanceOpenShift instanceOpenShift = new MyOCInstances().getMyOcs().get(oc);
			String typeapi="api";
			if ("deploymentconfigs".equalsIgnoreCase(resourcetype)
					|| "routes".equalsIgnoreCase(resourcetype)) {
				typeapi="oapi";
			}
			
			String url = instanceOpenShift.getUrl()+"/"+typeapi+"/v1/namespaces/"+project+"/"+resourcetype;
						
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
				Resource resource = new Resource(name, resourcetype,"true","");
				resources.add(resource);
			}
			
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return resources;
	}
	
	
	// Comparator

	public Map<String,ProjectCompare> compareInstances( String ocA, String ocB) {
		
		HashMap<String,ProjectCompare> projectCompare = new HashMap<>();	
		InstanceOpenShift instanceOpenShiftA = new MyOCInstances().getMyOcs().get(ocA);
		InstanceOpenShift instanceOpenShiftB = new MyOCInstances().getMyOcs().get(ocB);
		
		//HashMap<String, OpenshiftProject> mapProjectsInstanceA = new HashMap<>();
		Map<String, OpenshiftProject> mapProjectsInstanceA = OpenshiftProject.getMapProject(instanceOpenShiftA);
		//HashMap<String, OpenshiftProject> mapProjectsInstanceB = new HashMap<>();
		Map<String, OpenshiftProject> mapProjectsInstanceB = OpenshiftProject.getMapProject(instanceOpenShiftB);
		
		Map<String, PersistentVolume> mapVolumesA = PersistentVolume.getMapPV(instanceOpenShiftA);
		Map<String, PersistentVolume> mapVolumesB = PersistentVolume.getMapPV(instanceOpenShiftB);
		
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
			
			
			Map<String, Services> svcsA= Services.getMapSVC(instanceOpenShiftA, projectName);
			Map<String, Routes> routesA= Routes.getMapRoutes(instanceOpenShiftA, projectName);
			Map<String, Secret> secretsA= Secret.getMapSecrets(instanceOpenShiftA, projectName);
			Map<String, ConfigMap> cmA = ConfigMap.getMapResources(instanceOpenShiftA, projectName);
					
			
			
			Map<String, Services> svcsB= new HashMap<>();
			Map<String, Routes> routesB= new HashMap<>();
			Map<String, Secret> secretsB= new HashMap<>();
			Map<String, ConfigMap> cmB = new HashMap<>();
			
			
			if ( !(projectName.equalsIgnoreCase("kafka") )) {
				 svcsB= Services.getMapSVC(instanceOpenShiftB, projectName);
				 routesB= Routes.getMapRoutes(instanceOpenShiftB, projectName);
				 secretsB= Secret.getMapSecrets(instanceOpenShiftB, projectName);
				 cmB = ConfigMap.getMapResources(instanceOpenShiftB, projectName);
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

		DCCompare dcCompare = null;
		try {
			InstanceOpenShift instanceOpenShiftA = new MyOCInstances().getMyOcs().get(ocA);
			InstanceOpenShift instanceOpenShiftB = new MyOCInstances().getMyOcs().get(ocB);
			DeploymentConfig dcA = getDC(instanceOpenShiftA, projectNameA, dcNameA);
			DeploymentConfig dcB = getDC(instanceOpenShiftB, projectNameB, dcNameB);	
			
			dcCompare =new DCCompare(dcA, dcB);
			dcCompare.generateAllCompare();		
			for (ContainerCompare ct:dcCompare.getContainerCompare().values()) {
				ct.generateAllCompare();
			}
		} catch (Exception e) {
			logger.log(Level.INFO,e.getMessage());
		}
		return dcCompare;
	}
	
	public Map<String,DCCompare> compareDistinctProjects( String ocA, String ocB ,String projectNameA ,String projectNameB) {
		
		
		Map<String,DCCompare> dcCompare = new HashMap<>();
		
		InstanceOpenShift instanceOpenShiftA = new MyOCInstances().getMyOcs().get(ocA);
		InstanceOpenShift instanceOpenShiftB = new MyOCInstances().getMyOcs().get(ocB);
		
		/*
		HashMap<String, Services> svcsA= new HashMap<>();
		HashMap<String, Routes> routesA= new HashMap<>();
		HashMap<String, Secret> secretsA= new HashMap<>();
		*/
		
		Map<String, Services> svcsA= Services.getMapSVC(instanceOpenShiftA, projectNameA);
		Map<String, Routes> routesA= Routes.getMapRoutes(instanceOpenShiftA, projectNameA);
		Map<String, Secret> secretsA= Secret.getMapSecrets(instanceOpenShiftA, projectNameA);
		
		
		Map<String, Services> svcsB= Services.getMapSVC(instanceOpenShiftB, projectNameB);	
		Map<String, Routes> routesB= Routes.getMapRoutes(instanceOpenShiftB, projectNameB);
		Map<String, Secret> secretsB= Secret.getMapSecrets(instanceOpenShiftB, projectNameB);
		
		/*
		HashMap<String, Services> svcsB= new HashMap<>();
		HashMap<String, Routes> routesB= new HashMap<>();
		HashMap<String, Secret> secretsB= new HashMap<>();
		*/
		//HashMap<String, DeploymentConfig> mapdcA = new HashMap<>();
		Map<String, DeploymentConfig> mapdcA = DeploymentConfig.getMapDC(instanceOpenShiftA, projectNameA,svcsA,routesA,secretsA);
		//HashMap<String, DeploymentConfig> mapdcB = new HashMap<>();
		Map<String, DeploymentConfig> mapdcB = DeploymentConfig.getMapDC(instanceOpenShiftB, projectNameB,svcsB,routesB,secretsB);
		
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
