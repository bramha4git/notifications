package com.app.notification.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaTopicsModel {
	
	@JsonProperty("TopicName")
	private String topicName;

	@JsonProperty("TopicName")
	public String getTopicName() {
		return topicName;
	}

	@JsonProperty("TopicName")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public KafkaTopicsModel(String topicName) {
		super();
		this.topicName = topicName;
	}
	
	public KafkaTopicsModel() {
		super();
	}

	@Override
	public String toString() {
		return "topic [name=" + topicName + "]";		
	}
}
