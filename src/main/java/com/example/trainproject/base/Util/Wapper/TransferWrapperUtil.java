package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TransferWrapperUtil {

  // Default ObjectMapper with support for Java 8 time types like Instant
  // This line ensures Jackson can handle Instant and other Java 8 date/time types
  private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public static String toJson(TransferWrapper<?> wrapper) throws JsonProcessingException {
    // Serialize TransferWrapper to JSON string.
    // converts the wrapper to a JSON string.
    return DEFAULT_MAPPER.writeValueAsString(wrapper);
  }


  public static String toPrettyJson(TransferWrapper<?> wrapper) throws JsonProcessingException {
    // Prety JSON (for logs/debugging).
    return DEFAULT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);
  }


  public static TransferWrapper<?> fromJson(String json) throws JsonProcessingException {
    // Deserialize JSON string to TransferWrapper (without resolving specific DTO types).
    return DEFAULT_MAPPER.readValue(json, TransferWrapper.class);
  }

  public static TransferWrapper<? extends DataTransferObject> fromJsonSafely(String json) throws Exception {
    // Safe deserialization using registered DTO type mapping.
    // This should use the TransferDeserializer
    // This one uses the "dataType" string from the JSON and looks it up using your TransferTypeRegistry
    // Then it manually instructs the ObjectMapper to parse the data node using the correct subclass
    return new TransferDeserializer(DEFAULT_MAPPER).deserialize(json);
  }


  public static String toJson(TransferWrapper<?> wrapper, ObjectMapper mapper) throws JsonProcessingException {
    // basic deserialization, but data becomes a generic map
    // Optionl: Allow custom ObjectMapper usage
    return mapper.writeValueAsString(wrapper);
  }

  public static TransferWrapper<?> fromJson(String json, ObjectMapper mapper) throws JsonProcessingException {
    return mapper.readValue(json, TransferWrapper.class);
  }

//  Purpose   | Method                            | Type-Safe?  | Customizable?  | Notes

//  Serialize | TransferWrapperUtil.toJson()      | ✅           | ❌             | Default ObjectMapper
// | TransferWrapperSerializer.serialize()        | ✅           | ✅             | Custom ObjectMapper

//  Deserialize | TransferWrapperUtil.fromJson()  | ❌           | ❌             | Quick and simple
// | TransferWrapperUtil.fromJsonSafely()         | ✅           | ✅             | Uses DTO registry
}
