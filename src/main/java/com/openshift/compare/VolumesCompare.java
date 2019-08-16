package com.openshift.compare;

import com.openshift.resources.Volumes;

public class VolumesCompare {

	private String volumeAName;
	
	private String volumeBName;
	
	private Volumes volumeA;
	
	private Volumes volumeB;

	public VolumesCompare(Volumes volumeA, Volumes volumeB) {
		super();
		this.volumeA = volumeA;
		this.volumeB = volumeB;
		this.volumeAName = volumeA!=null?volumeA.getName():null;
		this.volumeBName = volumeB!=null?volumeB.getName():null;
		
	}

	public String getVolumeAName() {
		return volumeAName;
	}

	public void setVolumeAName(String volumeAName) {
		this.volumeAName = volumeAName;
	}

	public String getVolumeBName() {
		return volumeBName;
	}

	public void setVolumeBName(String volumeBName) {
		this.volumeBName = volumeBName;
	}

	public Volumes getVolumeA() {
		return volumeA;
	}

	public void setVolumeA(Volumes volumeA) {
		this.volumeA = volumeA;
	}

	public Volumes getVolumeB() {
		return volumeB;
	}

	public void setVolumeB(Volumes volumeB) {
		this.volumeB = volumeB;
	}
	
	
	
}
