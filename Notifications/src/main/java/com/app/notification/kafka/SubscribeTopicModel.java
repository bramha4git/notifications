package com.app.notification.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscribeTopicModel {

	public SubscribeTopicModel() {
		super();
	}

	@JsonProperty("TopicName")
	private String topicName;

	@JsonProperty("Principalname")
	private String principalName;

	@JsonProperty("Access")
	private String access;

	@JsonProperty("Host")
	private String host;

	@JsonProperty("HostAccess")
	private String hostAccess;

	@JsonProperty("Operation")
	private String operation;

	@JsonProperty("TopicName")
	public String getTopicName() {
		return topicName;
	}

	@JsonProperty("TopicName")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	@JsonProperty("Principalname")
	public String getPrincipalName() {
		return principalName;
	}

	@JsonProperty("Principalname")
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	@JsonProperty("Access")
	public String getAccess() {
		return access;
	}

	@JsonProperty("Access")
	public void setAccess(String access) {
		this.access = access;
	}

	@JsonProperty("Host")
	public String getHost() {
		return host;
	}

	@JsonProperty("Host")
	public void setHost(String host) {
		this.host = host;
	}

	@JsonProperty("HostAccess")
	public String getHostAccess() {
		return hostAccess;
	}

	@JsonProperty("HostAccess")
	public void setHostAccess(String hostAccess) {
		this.hostAccess = hostAccess;
	}

	@JsonProperty("Operation")
	public String getOperation() {
		return operation;
	}

	@JsonProperty("Operation")
	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "SubscribeTopicModel [topicName=" + topicName + ", principalName=" + principalName + ", access=" + access
				+ ", host=" + host + ", hostAccess=" + hostAccess + ", operation=" + operation + ", getTopicName()="
				+ getTopicName() + ", getPrincipalName()=" + getPrincipalName() + ", getAccess()=" + getAccess()
				+ ", getHost()=" + getHost() + ", getHostAccess()=" + getHostAccess() + ", getOperation()="
				+ getOperation() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
