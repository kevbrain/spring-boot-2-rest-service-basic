package com.openshift.resources;

import java.util.HashMap;

public class MyOCInstances {
	
	private HashMap<String, InstanceOpenShift> myOcs;
	
	public MyOCInstances() {
		this.myOcs = new  HashMap<String, InstanceOpenShift>();		
		myOcs.put("int", new InstanceOpenShift("INTEGRATION","https://ksr.openshift.com:8443", "Bearer gG4sFfzJ9h2rFVTYHHJzgZBGgnyQ9SURAHzxpfK7GeE"));
		myOcs.put("uat", new InstanceOpenShift("USER ACCEPTANCE TESTING","https://ksr.openshift.com:8443", "Bearer gG4sFfzJ9h2rFVTYHHJzgZBGgnyQ9SURAHzxpfK7GeE"));
		myOcs.put("prd", new InstanceOpenShift("PRODUCTION","https://ksr.openshift.com:8443", "Bearer gG4sFfzJ9h2rFVTYHHJzgZBGgnyQ9SURAHzxpfK7GeE"));
	}

	public MyOCInstances(HashMap<String, InstanceOpenShift> myOcs) {
		super();
		this.myOcs = myOcs;
	}

	public HashMap<String, InstanceOpenShift> getMyOcs() {
		return myOcs;
	}

	public void setMyOcs(HashMap<String, InstanceOpenShift> myOcs) {
		this.myOcs = myOcs;
	}

	
	
	

}
