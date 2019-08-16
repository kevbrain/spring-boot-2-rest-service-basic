package com.openshift.compare;

import java.util.HashMap;
import java.util.LinkedList;

import com.openshift.resources.Containers;


public class ContainerCompare {

	private String containerAName;
	
	private String containerBName;
	
	private Containers containerA;
	
	private Containers containerB;

	private HashMap<String, EnvCompare> envCompare;
	
	private HashMap<String, VolumesMountCompare> volumeMountCompare;
	
	public ContainerCompare(Containers containerA, Containers containerB) {
		super();
		this.containerA = containerA;
		this.containerB = containerB;
		this.containerAName = containerA!=null?containerA.getName():"";
		this.containerBName = containerB!=null?containerB.getName():"";
		
	}
	
	public void generateAllCompare() {
		generateEnvCompare();
		generateVolMountCompare();
		
	}
	
	public String diffMathCompare() {
		 Diff_match_patch dmp = new Diff_match_patch();
		 LinkedList<Diff_match_patch.Diff> diff = dmp.diff_main(containerBName,containerAName);
		 dmp.diff_cleanupSemantic(diff);
		 System.out.println("DIFF COMPARE = "+diff);
		 return dmp.diff_prettyHtml(diff);
	}
	
	public void generateEnvCompare() {
		System.out.println("generateEnvCompare");
		try {
			envCompare = new HashMap<>();
			if (containerA!=null) {
				for (String key:containerA.getEnv().keySet()) {
					if (!envCompare.containsKey(key)) {
						envCompare.put(key, new EnvCompare(containerA.getEnv().get(key), null));
					} else {
						envCompare.get(key).setValueA(containerA.getEnv().get(key));
					}
				}
			}
			if (containerB!=null) {
				for (String key:containerB.getEnv().keySet()) {
					if (!envCompare.containsKey(key)) {
						envCompare.put(key, new EnvCompare( null,containerB.getEnv().get(key)));
					} else {
						envCompare.get(key).setValueB(containerB.getEnv().get(key));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateVolMountCompare() {
		volumeMountCompare = new HashMap<>();
		if (containerA!=null && containerA.getVolmount()!=null) {
			for (String key:containerA.getVolmount().keySet()) {
				if (!volumeMountCompare.containsKey(key)) {
					volumeMountCompare.put(key, new VolumesMountCompare(containerA.getVolmount().get(key), null));
				} else {
					volumeMountCompare.get(key).setVolumeMountA(containerA.getVolmount().get(key));
				}
			}
		}
		if (containerB!=null && containerB.getVolmount()!=null) {
			for (String key:containerB.getVolmount().keySet()) {
				if (!volumeMountCompare.containsKey(key)) {
					volumeMountCompare.put(key, new VolumesMountCompare(null,containerB.getVolmount().get(key)));
				} else {
					volumeMountCompare.get(key).setVolumeMountB(containerB.getVolmount().get(key));
				}
			}
		}
	}

	public String getContainerAName() {
		return containerAName;
	}

	public void setContainerAName(String containerAName) {
		this.containerAName = containerAName;
	}

	public String getContainerBName() {
		return diffMathCompare();
	}

	public void setContainerBName(String containerBName) {
		this.containerBName = containerBName;
	}

	public Containers getContainerA() {
		return containerA;
	}

	public void setContainerA(Containers containerA) {
		this.containerA = containerA;
	}

	public Containers getContainerB() {
		return containerB;
	}

	public void setContainerB(Containers containerB) {
		this.containerB = containerB;
	}

	public HashMap<String, EnvCompare> getEnvCompare() {
		return envCompare;
	}

	public void setEnvCompare(HashMap<String, EnvCompare> envCompare) {
		this.envCompare = envCompare;
	}

	public HashMap<String, VolumesMountCompare> getVolumeMountCompare() {
		return volumeMountCompare;
	}

	public void setVolumeMountCompare(HashMap<String, VolumesMountCompare> volumeMountCompare) {
		this.volumeMountCompare = volumeMountCompare;
	}
	
	
	
	
}
