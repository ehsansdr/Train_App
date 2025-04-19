package com.example.trainproject.base.Util.Wapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransferWrapper<T extends DataTransferObject> {


  private T data;
  private String dataType;
  private String sourceProject;
  private String destinationProject;
  private String schemaVersion;
  private String correlationId;
  private Instant timestamp;

  public TransferWrapper(T data) {
    this.data = data;
    this.dataType = data != null ? data.getClass().getName() : null;
    this.schemaVersion = "1.0";
  }

  public TransferWrapper(T data, String sourceProject, String destinationProject) {
    this(data);
    this.sourceProject = sourceProject;
    this.destinationProject = destinationProject;
    this.correlationId = UUID.randomUUID().toString();
    this.timestamp = Instant.now();
  }

  public TransferWrapper(T data, String dataType,String sourceProject, String destinationProject) {
    this(data);
    this.dataType = dataType;
    this.sourceProject = sourceProject;
    this.destinationProject = destinationProject;
    this.correlationId = UUID.randomUUID().toString();
    this.timestamp = Instant.now();
  }

  public void validate() {
    if (data != null) {
      data.validate();
    }
  }

  public static <T extends DataTransferObject> TransferWrapper<T> of(T data, String source, String destination) {
    return new TransferWrapper<>(data, source, destination);
  }
  public static <T extends DataTransferObject> TransferWrapper<T> of(T data, String dataType, String source, String destination) {
    return new TransferWrapper<>(data, dataType ,source, destination);
  }

}

