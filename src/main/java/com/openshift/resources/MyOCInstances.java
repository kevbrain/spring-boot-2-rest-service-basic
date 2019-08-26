package com.openshift.resources;

import java.util.HashMap;

public class MyOCInstances {
	
	private HashMap<String, InstanceOpenShift> myOcs;
	
	public MyOCInstances() {
		this.myOcs = new  HashMap<String, InstanceOpenShift>();		
		myOcs.put("int", new InstanceOpenShift("INTEGRATION","https://ksr.openshift.com:8443", "Bearer aTG5NRWXg2z4j7PPDn4hS_Bfg-mZMo41vXwKrThwvKQ"));
		myOcs.put("uat", new InstanceOpenShift("USER ACCEPTANCE TESTING","https://ksr.openshift.com:8443", "Bearer aTG5NRWXg2z4j7PPDn4hS_Bfg-mZMo41vXwKrThwvKQ"));
		myOcs.put("prd", new InstanceOpenShift("PRODUCTION","https://ksr.openshift.com:8443", "Bearer aTG5NRWXg2z4j7PPDn4hS_Bfg-mZMo41vXwKrThwvKQ"));
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
