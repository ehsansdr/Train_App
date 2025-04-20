package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TransferWrapperKafkaDeserializer implements Deserializer<TransferWrapper<? extends DataTransferObject>> {

  private final ObjectMapper objectMapper;
  private final TransferDeserializer transferDeserializer;

  public TransferWrapperKafkaDeserializer() {
    // Initialize the ObjectMapper with JavaTimeModule for Java 8 date types like Instant
    this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    this.transferDeserializer = new TransferDeserializer(objectMapper);
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    // Optionally configure any properties if needed
  }

  @Override
  public TransferWrapper<? extends DataTransferObject> deserialize(String topic, byte[] data) {
    try {
      // Deserialize the JSON data into a TransferWrapper object using TransferDeserializer
      String json = new String(data);
      return transferDeserializer.deserialize(json);
    } catch (Exception e) {
      e.printStackTrace();
      // Log or throw an appropriate exception if deserialization fails
      return null;
    }
  }

  @Override
  public void close() {
    // Optional: Close resources if needed
  }
}
