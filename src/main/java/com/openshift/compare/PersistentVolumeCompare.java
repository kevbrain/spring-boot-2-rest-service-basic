package com.openshift.compare;


import com.openshift.resources.PersistentVolume;

public class PersistentVolumeCompare {

	private PersistentVolume persistentVolumeA;
	
	private PersistentVolume persistentVolumeB;
	
	

	public PersistentVolumeCompare(PersistentVolume persistentVolumeA, PersistentVolume persistentVolumeB) {
		super();
		this.persistentVolumeA = persistentVolumeA;
		this.persistentVolumeB = persistentVolumeB;
	}

	public PersistentVolume getPersistentVolumeA() {
		return persistentVolumeA;
	}

	public void setPersistentVolumeA(PersistentVolume persistentVolumeA) {
		this.persistentVolumeA = persistentVolumeA;
	}

	public PersistentVolume getPersistentVolumeB() {
		return persistentVolumeB;
	}

	public void setPersistentVolumeB(PersistentVolume persistentVolumeB) {
		this.persistentVolumeB = persistentVolumeB;
	}
	
	
}
