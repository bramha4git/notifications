package com.app.notification.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.notification.kafka.KafkaProducer;
import com.app.notification.kafka.KafkaTopicsModel;
import com.app.notification.kafka.PublishMessageModel;
import com.app.notification.kafka.SubscribeTopicModel;
import com.app.notification.kafka.kafkaTopicsWrapper;
import com.app.notification.kafka.service.KafkaService;

@RestController
@RequestMapping("/api")
public class KafkaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

	@Autowired
	KafkaService kafkaService;

	@Autowired
	KafkaAdmin kafkaAdmin;
	
	@Autowired
	KafkaProducer kafkaProducer;

	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	private void createTopic(@RequestBody kafkaTopicsWrapper topicNames) {		
		try {
			Collection<NewTopic> topicsCollection = new ArrayList<>();
			for (KafkaTopicsModel topicModel: topicNames.getTopics()){
				topicsCollection.add(new NewTopic(topicModel.getTopicName(),1,(short) 1));
			}
			kafkaService.addTopicsIfNeeded(AdminClient.create(kafkaAdmin.getConfig()),topicsCollection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping(value = "/publish", consumes = "application/json", produces = "application/json")
	private ResponseEntity<String> publishMessage(@RequestBody PublishMessageModel topicObj) {		
		try {
			System.out.println("topicObj.getTopicName()::"+topicObj.getTopicName());
			System.out.println("topicObj.getMessage()::"+topicObj.getMessage());
			kafkaProducer.sendMessage(topicObj.getTopicName(), topicObj.getMessage());
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@PostMapping(value = "/subscribe", consumes = "application/json", produces = "application/json")
	private void subscribeTopic(@RequestBody SubscribeTopicModel topicObj) {		
		try {
			kafkaService.subscribe(AdminClient.create(kafkaAdmin.getConfig()),topicObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
