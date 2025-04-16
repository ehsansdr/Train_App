package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransferWrapperUtil {
  // both convenience and good design
  private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

  public static String toJson(TransferWrapper<?> wrapper) throws JsonProcessingException {
    return DEFAULT_MAPPER.writeValueAsString(wrapper);
  }

  public static TransferWrapper<?> fromJson(String json) throws JsonProcessingException {
    return DEFAULT_MAPPER.readValue(json, TransferWrapper.class);
  }
  public static TransferWrapper<? extends DataTransferObject> fromJsonSafely(String json) throws Exception {
    return new TransferDeserializer(DEFAULT_MAPPER).deserialize(json);
  }

}

