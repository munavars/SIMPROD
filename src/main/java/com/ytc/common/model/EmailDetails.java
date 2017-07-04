package com.ytc.common.model;

import java.util.List;

public class EmailDetails {
	private String host;
	private String port;
	private String userName;
	private String password;
	
	private String fromAddress;
	private List<String> toAddress;
	private String subject;
	private String text;
	private List<String> ccAddress;
	
	private StringBuilder toNames;
	
	private String environment;
	private byte[] attachment;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public List<String> getToAddress() {
		return toAddress;
	}
	public void setToAddress(List<String> toAddress) {
		this.toAddress = toAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<String> getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(List<String> ccAddress) {
		this.ccAddress = ccAddress;
	}
	public StringBuilder getToNames() {
		return toNames;
	}
	public void setToNames(StringBuilder toNames) {
		this.toNames = toNames;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
}
