package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
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

    Instant timestamp = node.has("timestamp") ? Instant.ofEpochSecond(
        node.get("timestamp").asLong(),
        node.get("timestamp").decimalValue().remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(1_000_000_000)).longValue()
    ) : null;

    TransferWrapper<DataTransferObject> wrapper = new TransferWrapper<>(data, sourceProject, destinationProject);
    wrapper.setSchemaVersion(schemaVersion);
    wrapper.setCorrelationId(correlationId);
    wrapper.setTimestamp(timestamp);

    return wrapper;
  }


  // Helper method to safely extract text fields from the JSON
  private String getTextOrNull(JsonNode node, String field) {
    return node.has(field) ? node.get(field).asText() : null;
  }
}
