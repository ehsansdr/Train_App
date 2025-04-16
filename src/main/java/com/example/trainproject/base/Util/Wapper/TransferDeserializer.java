package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransferDeserializer {
  //  Deserialize Safely with ObjectMapper

  private final ObjectMapper objectMapper;

  public TransferDeserializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public TransferWrapper<? extends DataTransferObject> deserialize(String json) throws Exception {
    // Step 1: Deserialize into raw TransferWrapper (data is LinkedHashMap)
    JsonNode node = objectMapper.readTree(json);
    String dataType = node.get("dataType").asText();

    Class<? extends DataTransferObject> dtoClass = TransferTypeRegistry.getClassForType(dataType);
    if (dtoClass == null) {
      throw new IllegalArgumentException("Unrecognized dataType: " + dataType);
    }

    JsonNode dataNode = node.get("data");
    DataTransferObject data = objectMapper.treeToValue(dataNode, dtoClass);

    TransferWrapper<? extends DataTransferObject> wrapper = objectMapper.treeToValue(node, TransferWrapper.class);
    wrapper = new TransferWrapper<>(data, wrapper.getSourceProject(), wrapper.getDestinationProject());
    return wrapper;
  }
}

