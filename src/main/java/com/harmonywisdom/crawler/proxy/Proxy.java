package com.harmonywisdom.crawler.proxy;

public class Proxy {
	
	String host;
	String port;
	String name;
	String password;
	Boolean isLoginRequired;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsLoginRequired() {
		return isLoginRequired;
	}
	public void setIsLoginRequired(Boolean isLoginRequired) {
		this.isLoginRequired = isLoginRequired;
	}

}
