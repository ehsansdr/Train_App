package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class TransferWrapperSerializer {

  private final ObjectMapper objectMapper;

  public TransferWrapperSerializer() {
    this.objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule()) // this handles Instant and other Java 8 date/time types
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // serialize dates as ISO-8601 instead of timestamp
  }

  public String serialize(TransferWrapper<?> wrapper) throws JsonProcessingException {
    return objectMapper.writeValueAsString(wrapper);
  }

  public TransferWrapper<?> deserialize(String json) throws IOException {
    return objectMapper.readValue(json, TransferWrapper.class);
  }
}


