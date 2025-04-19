package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

public class TransferDeserializer {

  private final ObjectMapper objectMapper;

  public TransferDeserializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public TransferWrapper<? extends DataTransferObject> deserialize(String json) throws Exception {
    JsonNode node = objectMapper.readTree(json);

    String dataType = node.get("dataType").asText();
    Class<? extends DataTransferObject> dtoClass = TransferTypeRegistry.getClassForType(dataType);
    if (dtoClass == null) {
      throw new IllegalArgumentException("Unrecognized dataType: " + dataType);
    }

    JsonNode dataNode = node.get("data");
    DataTransferObject data = objectMapper.treeToValue(dataNode, dtoClass);

    String sourceProject = getTextOrNull(node, "sourceProject");
    String destinationProject = getTextOrNull(node, "destinationProject");
    String schemaVersion = getTextOrNull(node, "schemaVersion");
    String correlationId = getTextOrNull(node, "correlationId");
    Instant timestamp = node.has("timestamp") ? Instant.parse(node.get("timestamp").asText()) : null;

    TransferWrapper<DataTransferObject> wrapper = new TransferWrapper<>(data, sourceProject, destinationProject);
    wrapper.setSchemaVersion(schemaVersion);
    wrapper.setCorrelationId(correlationId);
    wrapper.setTimestamp(timestamp);

    return wrapper;
  }

  private String getTextOrNull(JsonNode node, String field) {
    return node.has(field) ? node.get(field).asText() : null;
  }
}

