package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaTransferWrapperSerializer implements Serializer<TransferWrapper<?>> {

  private final ObjectMapper objectMapper;

  public KafkaTransferWrapperSerializer() {
    this.objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule()); // for Instant and other Java 8 types
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    // optional: you can configure any settings here if needed
  }

  @Override
  public byte[] serialize(String topic, TransferWrapper<?> wrapper) {
    try {
      // Serialize the TransferWrapper to JSON
      String json = objectMapper.writeValueAsString(wrapper);
      return json.getBytes();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void close() {
    // optional: cleanup if needed
  }
}
