package com.openshift.resources;

public class ListenPort {
	
	private String name;
	
	private String protocol;
	
	private int port;

	public ListenPort(String name, String protocol, int port) {
		super();
		this.name = name;
		this.protocol = protocol;
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
	
}
