package com.app.notification.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublishMessageModel {
	
	public PublishMessageModel() {
		super();
	}
	
	@JsonProperty("TopicName")
	private String topicName;
	
	@JsonProperty("Message")
	private String message;	
	
	@JsonProperty("TopicName")
	public String getTopicName() {
		return topicName;
	}

	@JsonProperty("TopicName")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	@JsonProperty("Message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("Message")
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PublishMessageModel [topicName=" + topicName + ", message=" + message + "]";
	}


}
