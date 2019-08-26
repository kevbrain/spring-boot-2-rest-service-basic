package com.openshift.compare;

import java.util.LinkedList;

import com.openshift.resources.Resource;

public class ResourceCompare {
	
	private Resource resourceA;
	
	private Resource resourceB;
	
	
	
	public ResourceCompare(Resource resourceA, Resource resourceB) {
		super();
		this.resourceA = resourceA;
		this.resourceB = resourceB;
	}


	public String compareValue(String strA,String strB) {
		if (strA==null) strA="";
		if (strB==null) strB="";
		Diff_match_patch dmp = new Diff_match_patch();
		LinkedList<Diff_match_patch.Diff> diff = dmp.diff_main(strA,strB);
		dmp.diff_cleanupSemantic(diff);
		return dmp.diff_prettyHtml(diff);
	}
	

	public Resource getResourceA() {
		return resourceA;
	}


	public void setResourceA(Resource resourceA) {
		this.resourceA = resourceA;
	}


	public Resource getResourceB() {
		return resourceB;
	}


	public void setResourceB(Resource resourceB) {
		this.resourceB = resourceB;
	}
	
	

}
