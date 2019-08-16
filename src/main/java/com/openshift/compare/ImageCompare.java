package com.openshift.compare;

import com.openshift.resources.TriggerImageChange;

public class ImageCompare {
	
	private TriggerImageChange imageA;
	
	private TriggerImageChange imageB;

	public ImageCompare(TriggerImageChange imageA, TriggerImageChange imageB) {
		super();
		this.imageA = imageA;
		this.imageB = imageB;
	}

	public TriggerImageChange getImageA() {
		return imageA;
	}

	public void setImageA(TriggerImageChange imageA) {
		this.imageA = imageA;
	}

	public TriggerImageChange getImageB() {
		return imageB;
	}

	public void setImageB(TriggerImageChange imageB) {
		this.imageB = imageB;
	}
	
	

}
