package com.jagriti;

public class ProxyConfig {
	private String host;
	private Integer port;
	private String username;
	private String password;

	public ProxyConfig() {
		this.host = null;
		this.port = null;
	}

	public ProxyConfig(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

