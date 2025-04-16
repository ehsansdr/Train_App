package com.example.trainproject.base.Service;
import com.example.trainproject.base.Model.KafkaProduceMessage;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.example.trainproject.base.Util.Wapper.TransferDeserializer;
import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  private ObjectMapper objectMapper = new ObjectMapper();

  private TransferDeserializer transferDeserializer = new TransferDeserializer(objectMapper);


  @KafkaListener(topics = "${my.kafka.topic}" , groupId = "${my.kafka.consumer-group-id}" )
  public void consumeMessage(KafkaProduceMessage message) {
    System.out.println(message.toString() +  " =====> received perfectly");
  }

  @KafkaListener(topics = "${my.kafka.topic}")
  public void consume(String messageJson) throws Exception {
    TransferWrapper<?> wrapper = objectMapper.readValue(messageJson, TransferWrapper.class);
    System.out.println("Received transfer from: " + wrapper.getSourceProject());

    // Deserialize data
    Class<?> classType = Class.forName(wrapper.getDataType());
    DataTransferObject data = (DataTransferObject) objectMapper.convertValue(wrapper.getData(), classType);
    data.validate();

  }

  @KafkaListener(topics = "${my.kafka.topic}", groupId = "${my.kafka.consumer-group-id}")
  public void consumeRawMessage(String message) {
    try {
      TransferWrapper<?> wrapper = transferDeserializer.deserialize(message);
      System.out.println("Received: " + wrapper.getData());
      wrapper.validate();
      // Route or use the data object!
    } catch (Exception ex) {
      log.error("Invalid transfer message", ex);
    }
  }



}