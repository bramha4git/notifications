package com.app.notification.kafka.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import com.app.notification.kafka.SubscribeTopicModel;

@Service
public class KafkaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);
	private static final long operationTimeout = 30;
	
	public void addTopicsIfNeeded(AdminClient adminClient, Collection<NewTopic> topics) {
	    if (topics.size() > 0) {
	        Map<String, NewTopic> topicNameToTopic = new HashMap<>();
	        topics.forEach(t -> topicNameToTopic.compute(t.name(), (k, v) -> t));
	        DescribeTopicsResult topicInfo = adminClient
	                .describeTopics(topics.stream()
	                        .map(NewTopic::name)
	                        .collect(Collectors.toList()));
	        List<NewTopic> topicsToAdd = new ArrayList<>();
	        Map<String, NewPartitions> topicsToModify = checkPartitions(topicNameToTopic, topicInfo, topicsToAdd);
	        if (topicsToAdd.size() > 0) {
	            addTopics(adminClient, topicsToAdd);
	        }
	        if (topicsToModify.size() > 0) {
	            modifyTopics(adminClient, topicsToModify);
	        }
	    }
	}
	
	private void modifyTopics(AdminClient adminClient, Map<String, NewPartitions> topicsToModify) {
		// TODO Auto-generated method stub
		
	}

	private void addTopics(AdminClient adminClient, List<NewTopic> topicsToAdd) {
		adminClient.createTopics(topicsToAdd);		
	}

	private Map<String, NewPartitions> checkPartitions(Map<String, NewTopic> topicNameToTopic,
	        DescribeTopicsResult topicInfo, List<NewTopic> topicsToAdd) {

	    Map<String, NewPartitions> topicsToModify = new HashMap<>();
	    topicInfo.values().forEach((n, f) -> {
	        NewTopic topic = topicNameToTopic.get(n);
	        try {
	            TopicDescription topicDescription;
	            topicDescription = f.get(KafkaService.operationTimeout, TimeUnit.SECONDS);
	            if (topic.numPartitions() < topicDescription.partitions().size()) {
	                if (LOGGER.isInfoEnabled()) {
	                    LOGGER.info(String.format(
	                        "Topic '%s' exists but has a different partition count: %d not %d", n,
	                        topicDescription.partitions().size(), topic.numPartitions()));
	                }
	            }
	            else if (topic.numPartitions() > topicDescription.partitions().size()) {
	                if (LOGGER.isInfoEnabled()) {
	                    LOGGER.info(String.format(
	                        "Topic '%s' exists but has a different partition count: %d not %d, increasing "
	                        + "if the broker supports it", n,
	                        topicDescription.partitions().size(), topic.numPartitions()));
	                }
	                topicsToModify.put(n, NewPartitions.increaseTo(topic.numPartitions()));
	            }
	        }catch (java.util.concurrent.TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        catch (TimeoutException e) {
	            throw new KafkaException("Timed out waiting to get existing topics", e);
	        }
	        catch (ExecutionException e) {
	            topicsToAdd.add(topic);
	        }
	    });
	    return topicsToModify;
	}
	
	
	public void subscribe(AdminClient adminClient,SubscribeTopicModel topicObj) {
		Collection<AclBinding> acls = new ArrayList<>();
		
		ResourcePattern resource = new ResourcePattern(ResourceType.TOPIC,topicObj.getTopicName(),PatternType.MATCH);
		AccessControlEntry aclControls = new AccessControlEntry(topicObj.getPrincipalName(), topicObj.getHost(), AclOperation.READ, AclPermissionType.ALLOW);
		
		acls.add(new AclBinding(resource, aclControls));
		adminClient.createAcls(acls);
	}

}
