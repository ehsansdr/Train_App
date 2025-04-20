package com.example.trainproject.base.Util.Wapper;

import com.example.trainproject.base.Util.Wapper.TransferWrapper;
import com.example.trainproject.base.Util.Wapper.TransferDeserializer;
import com.example.trainproject.base.Util.Wapper.DataTransferObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class TransferWrapperKafkaDeserializer implements Deserializer<TransferWrapper<? extends DataTransferObject>> {

  private final ObjectMapper objectMapper;

  public TransferWrapperKafkaDeserializer() {
    this.objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule()); // for Instant and other Java 8 types
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    // optional
  }

  @Override
  public TransferWrapper<? extends DataTransferObject> deserialize(String topic, byte[] data) {
    try {
      String json = new String(data);
      TransferDeserializer deserializer = new TransferDeserializer(objectMapper);
      return deserializer.deserialize(json);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void close() {
    // optional
  }
}

