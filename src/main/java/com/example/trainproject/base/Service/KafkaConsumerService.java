package com.example.trainproject.base.Service;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

  @Value(value = "${my.kafka.topic}")
  private static String kafkaTopic;

  @Value(value = "${my.kafka.consumer-group-id}")
  private static String groupId;


  @KafkaListener(topics = "${my.kafka.topic}" , groupId =" ${my.kafka.consumer-group-id}" )
  public void consumeMessage(KafkaProduceMessage message) {
    System.out.println(message.toString() +  " =====> received perfectly");
  }


}