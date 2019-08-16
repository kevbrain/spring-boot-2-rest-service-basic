package com.openshift.resources;

public class Routes {
	
	private String name;
	
	private String host;
	
	private String tls_insecureEdgeTerminationPolicy;
	
	private String tls_termination;
	
	private String service;

	public Routes(String name, String host) {
		super();
		this.name = name;
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getTls_insecureEdgeTerminationPolicy() {
		return tls_insecureEdgeTerminationPolicy;
	}

	public void setTls_insecureEdgeTerminationPolicy(String tls_insecureEdgeTerminationPolicy) {
		this.tls_insecureEdgeTerminationPolicy = tls_insecureEdgeTerminationPolicy;
	}

	public String getTls_termination() {
		return tls_termination;
	}

	public void setTls_termination(String tls_termination) {
		this.tls_termination = tls_termination;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
	
	

}
