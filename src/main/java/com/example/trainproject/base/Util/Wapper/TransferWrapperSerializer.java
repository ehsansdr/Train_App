package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class TransferWrapperSerializer {
  // best
  private final ObjectMapper objectMapper;

  public TransferWrapperSerializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String serialize(TransferWrapper<?> wrapper) throws JsonProcessingException {
    return objectMapper.writeValueAsString(wrapper);
  }

  public TransferWrapper<?> deserialize(String json) throws IOException {
    return objectMapper.readValue(json, TransferWrapper.class);
  }
}

