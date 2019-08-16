package com.openshift.compare;

import com.openshift.resources.ConfigMap;

public class ConfigMapCompare  extends ResourceCompare{

	private ConfigMap configMapA;
	
	private ConfigMap configMapB;

	public ConfigMapCompare(ConfigMap configMapA, ConfigMap configMapB) {
		super();
		this.configMapA = configMapA;
		this.configMapB = configMapB;
	}

	public ConfigMap getConfigMapA() {
		return configMapA;
	}

	public void setConfigMapA(ConfigMap configMapA) {
		this.configMapA = configMapA;
	}

	public ConfigMap getConfigMapB() {
		return configMapB;
	}

	public void setConfigMapB(ConfigMap configMapB) {
		this.configMapB = configMapB;
	}
	
	public void compare() {
		if (configMapA!=null && configMapB!=null && configMapA.getData()!=null && configMapB.getData()!=null) {
			for (String keydatat :configMapA.getData().keySet()) {				
				configMapB.getData().put(keydatat, configMapB.getData().get(keydatat).replace("DAPBgNVHRMBAf8EBTADAQH/MA0G", "PowCpYtRGPFO5P2x4G2sAiTwMUk"));
				configMapB.getData().put(keydatat, configMapB.getData().get(keydatat).replace("BEGIN", "BAD"));
				configMapB.getData().put(keydatat, compareValue(configMapB.getData().get(keydatat),configMapA.getData().get(keydatat)));
			}
		}
	}
		
	
}
