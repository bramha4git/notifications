package com.app.notification.kafka;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class kafkaTopicsWrapper {

	@JsonProperty("Topics")
	private List<KafkaTopicsModel> topics;

	@JsonProperty("Topics")
    public List<KafkaTopicsModel> getTopics() {
		return topics;
	}

	public kafkaTopicsWrapper() {
		super();
	}

	@JsonProperty("Topics")
	public void setTopics(List<KafkaTopicsModel> topics) {
		this.topics = topics;
	}

	public kafkaTopicsWrapper(List<KafkaTopicsModel> topics) {
		super();
		this.topics = topics;
	}

	
}
