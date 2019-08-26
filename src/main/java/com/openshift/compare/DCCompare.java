package com.openshift.compare;

import java.util.HashMap;
import java.util.Map;

import com.openshift.resources.Containers;
import com.openshift.resources.DeploymentConfig;
import com.openshift.resources.Services;
import com.openshift.resources.TriggerImageChange;
import com.openshift.resources.Volumes;

public class DCCompare {

	private String dcAName;
	
	private String dcBName;

	private DeploymentConfig dcA;
	
	private DeploymentConfig dcB;
	
	private Map<String, ContainerCompare> containerCompare;
	
	private Map<String, VolumesCompare> volumeCompare;
	
	private Map<String, ImageCompare> imageCompare;
	
	private Map<String, ServiceCompare> serviceCompare;
	
	private Map<String, RouteCompare> routeCompare;
	

	public DCCompare(DeploymentConfig dcA, DeploymentConfig dcB) {
		super();
		this.dcA = dcA;
		this.dcB = dcB;
		this.dcAName=dcA!=null?dcA.getName():null;
		this.dcBName=dcB!=null?dcB.getName():null;
	}
	
	public void generateAllCompare() {
		generatecontainerCompare();
		generateVolumeCompare();
		generateImageCompare();
		generateServiceCompare();
	}
	
	
	
	public void generatecontainerCompare() {
		this.containerCompare = new HashMap<>();
		Map<String, Containers> mapContainersA = dcA!=null?dcA.getContainers():new HashMap<String, Containers>();
		Map<String, Containers> mapContainersB = dcB!=null?dcB.getContainers():new HashMap<String, Containers>();
		
		for (String key:mapContainersA.keySet()) {
			if (!containerCompare.containsKey(key)) {
				containerCompare.put(key, new ContainerCompare(mapContainersA.get(key), null));
			} else {
				containerCompare.get(key).setContainerAName(key);
				containerCompare.get(key).setContainerA(mapContainersA.get(key));
			}
		}
		
		for (String key:mapContainersB.keySet()) {
			if (!containerCompare.containsKey(key)) {
				containerCompare.put(key, new ContainerCompare(null,mapContainersB.get(key)));
			} else {
				containerCompare.get(key).setContainerBName(key);
				containerCompare.get(key).setContainerB(mapContainersB.get(key));
			}
		}
	}
	
	public void generateVolumeCompare() {
		this.volumeCompare = new HashMap<>();
		Map<String, Volumes> mapVolumesA = dcA!=null?dcA.getVolumes():new HashMap<String, Volumes>();
		Map<String, Volumes> mapVolumesB = dcB!=null?dcB.getVolumes():new HashMap<String, Volumes>();
				
		for (String key:mapVolumesA.keySet()) {
			if (!volumeCompare.containsKey(key)) {
				volumeCompare.put(key, new VolumesCompare(mapVolumesA.get(key),null));					
			} else {
				volumeCompare.get(key).setVolumeAName(key);
				volumeCompare.get(key).setVolumeA(mapVolumesA.get(key));
			}
		}
		
		for (String key:mapVolumesB.keySet()) {
			if (!volumeCompare.containsKey(key)) {
				volumeCompare.put(key, new VolumesCompare(null,mapVolumesB.get(key)));
			} else {
				volumeCompare.get(key).setVolumeBName(key);
				volumeCompare.get(key).setVolumeB(mapVolumesB.get(key));
			}
		}
	}
	
	
	
	public void generateImageCompare() {
		this.imageCompare = new HashMap<>();
		Map<String, TriggerImageChange> mapImagesA = dcA!=null?dcA.getTriggerImageChange():new HashMap<String, TriggerImageChange>();
		Map<String, TriggerImageChange> mapImagesB = dcB!=null?dcB.getTriggerImageChange():new HashMap<String, TriggerImageChange>();
		
		for (String key:mapImagesA.keySet()) {
			if (!imageCompare.containsKey(key)) {
				imageCompare.put(key, new ImageCompare(mapImagesA.get(key), null));
			} else {
				imageCompare.get(key).setImageA(mapImagesA.get(key));
			}
		}
		
		for (String key:mapImagesB.keySet()) {
			if (!imageCompare.containsKey(key)) {
				imageCompare.put(key, new ImageCompare(null,mapImagesB.get(key)));
			} else {
				imageCompare.get(key).setImageB(mapImagesB.get(key));
			}
		}
	}

	public void generateServiceCompare() {
		this.serviceCompare = new HashMap<>();
		//HashMap<String, Services> mapServiceA = dcA!=null?new HashMap<>()dcA.getServicesLinked().getName().getServicesLinked():new HashMap<String, Services>();
		//HashMap<String, Services> mapServiceB = dcB!=null?dcB.getServicesLinked():new HashMap<String, Services>();
		
		Map<String, Services> mapServiceA = new HashMap<String, Services>();
		Map<String, Services> mapServiceB = new HashMap<String, Services>();
		
		
		for (String key:mapServiceA.keySet()) {
			if (!serviceCompare.containsKey(key)) {
				serviceCompare.put(key, new ServiceCompare(mapServiceA.get(key), null));
			} else {
				serviceCompare.get(key).setServiceA(mapServiceA.get(key));
			}
			
		}
		for (String key:mapServiceB.keySet()) {
			if (!serviceCompare.containsKey(key)) {
				serviceCompare.put(key, new ServiceCompare(null,mapServiceB.get(key)));
			} else {
				serviceCompare.get(key).setServiceB(mapServiceB.get(key));
			}
		}
		
		for (ServiceCompare svccomp:serviceCompare.values()) {
			svccomp.generateRoutesCompare();
		}
	}
	
	public String getDcAName() {
		return dcAName;
	}

	public void setDcAName(String dcAName) {
		this.dcAName = dcAName;
	}

	public String getDcBName() {
		return dcBName;
	}

	public void setDcBName(String dcBName) {
		this.dcBName = dcBName;
	}

	public DeploymentConfig getDcA() {
		return dcA;
	}

	public void setDcA(DeploymentConfig dcA) {
		this.dcA = dcA;
	}

	public DeploymentConfig getDcB() {
		return dcB;
	}

	public void setDcB(DeploymentConfig dcB) {
		this.dcB = dcB;
	}

	public Map<String, ContainerCompare> getContainerCompare() {
		return containerCompare;
	}

	public void setContainerCompare(Map<String, ContainerCompare> containerCompare) {
		this.containerCompare = containerCompare;
	}

	public Map<String, VolumesCompare> getVolumeCompare() {
		return volumeCompare;
	}

	public void setVolumeCompare(Map<String, VolumesCompare> volumeCompare) {
		this.volumeCompare = volumeCompare;
	}

	public Map<String, ImageCompare> getImageCompare() {
		return imageCompare;
	}

	public void setImageCompare(Map<String, ImageCompare> imageCompare) {
		this.imageCompare = imageCompare;
	}

	public Map<String, ServiceCompare> getServiceCompare() {
		return serviceCompare;
	}

	public void setServiceCompare(Map<String, ServiceCompare> serviceCompare) {
		this.serviceCompare = serviceCompare;
	}

	public Map<String, RouteCompare> getRouteCompare() {
		return routeCompare;
	}

	public void setRouteCompare(Map<String, RouteCompare> routeCompare) {
		this.routeCompare = routeCompare;
	}

	
	
	
}
