package com.example.trainproject.base.Util.Wapper;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransferWrapper<T extends DataTransferObject> {

  private T data;
  private String dataType;
  private String sourceProject;
  private String destinationProject;
  private String schemaVersion; // for future-proofing
  private String correlationId; // Unique ID for tracing
  private Instant timestamp; // When this transfer was created


  public TransferWrapper(T data) {
    this.data = data;
    this.dataType = data != null ? data.getClass().getName() : null;
    this.schemaVersion = "1.0"; // default
  }

  public TransferWrapper(T data, String sourceProject, String destinationProject) {
    this(data);
    this.sourceProject = sourceProject;
    this.destinationProject = destinationProject;
    this.correlationId = UUID.randomUUID().toString(); // unique trace
    this.timestamp = Instant.now(); // creation time
  }

  public void validate() {
    if (data != null) {
      data.validate(); // delegate to the data’s validation logic
    }
  }

  public static <T extends DataTransferObject> TransferWrapper<T> of(T data, String source, String dest) {
    return new TransferWrapper<>(data, source, dest);
  }
}

