package com.example.trainproject.util;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.InvalidTopicException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaUtil {

  @Value("${spring.kafka.bootstrap-servers}")
  String kafkaBootstrapServers;


  public void monitorExistingTopic() {

    try (AdminClient adminClient = AdminClient.create(Map.of(
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers
    ))) {
      Set<String> topics = adminClient.listTopics().names().get();
      log.info("Created topic: {}", topics);
    } catch (ExecutionException | InterruptedException e) {
      if (e.getCause() instanceof InvalidTopicException) {
        System.err.println("❌ InvalidTopicException: " + e.getCause().getMessage());
      } else {
        System.err.println("❌ Failed to create topic: " + e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public CreateTopicsResult topicsCreator(String nameOfTheTopic, int numPartitions, int replicationFactor) {
    try (AdminClient adminClient = AdminClient.create(Map.of(
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers
    ))) {

      NewTopic newTopic = new NewTopic(nameOfTheTopic, numPartitions, (short) replicationFactor);

      Set<String> topics = adminClient.listTopics().names().get();
      log.info("Created topic: {}", topics);
//				if (!topics.contains(topicName)) {
//					adminClient.createTopics(List.of(newTopic)).all().get();
//					log.info("Created topic: {}" ,topicName);
//				} else {
//					log.warn("Topic already exists: {}", topicName);
//				}

      CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));

      result.all().get(); // This blocks until it's done
      System.out.println("✅ Topic created: " + nameOfTheTopic);
      return result;
    } catch (ExecutionException | InterruptedException e) {
      if (e.getCause() instanceof InvalidTopicException) {
        System.err.println("❌ InvalidTopicException: " + e.getCause().getMessage());
      } else {
        System.err.println("❌ Failed to create topic: " + e.getMessage());
      }
    }
    return null;
  }

}

