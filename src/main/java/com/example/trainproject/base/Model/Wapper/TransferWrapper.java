package com.example.trainproject.base.Model.Wapper;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransferWrapper<T> {

  private T data;
  private String dataType; // Optional: to store the type of the data
  private String sourceProject; // Optional: to identify the source of the data
  private String destinationProject; // Optional: to identify the intended destination

  public TransferWrapper(T data) {
    this.data = data;
    this.dataType = data != null ? data.getClass().getName() : "null";
  }

  // Optional helper constructor
  public TransferWrapper(T data, String sourceProject, String destinationProject) {
    this.data = data;
    this.dataType = data != null ? data.getClass().getName() : "null";
    this.sourceProject = sourceProject;
    this.destinationProject = destinationProject;
  }
}
