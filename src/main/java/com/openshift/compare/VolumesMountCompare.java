package com.openshift.compare;

import com.openshift.resources.VolumeMount;

public class VolumesMountCompare {
	
	private VolumeMount volumeMountA;
	
	private VolumeMount volumeMountB;

	public VolumesMountCompare(VolumeMount volumeMountA, VolumeMount volumeMountB) {
		super();
		this.volumeMountA = volumeMountA;
		this.volumeMountB = volumeMountB;
	}

	public VolumeMount getVolumeMountA() {
		return volumeMountA;
	}

	public void setVolumeMountA(VolumeMount volumeMountA) {
		this.volumeMountA = volumeMountA;
	}

	public VolumeMount getVolumeMountB() {
		return volumeMountB;
	}

	public void setVolumeMountB(VolumeMount volumeMountB) {
		this.volumeMountB = volumeMountB;
	}
	
	

}
