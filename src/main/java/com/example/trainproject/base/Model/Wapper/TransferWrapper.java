package com.example.trainproject.base.Model.Wapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferWrapper<T> {

  private T data;
  private String dataType; // Optional: to store the type of the data
  private String sourceProject; // Optional: to identify the source of the data
  private String destinationProject; // Optional: to identify the intended destination

  public TransferWrapper(T data) {
    // The constructor with only 'data' is kept as it has custom logic for dataType.
    this.data = data;
    this.dataType = data != null ? data.getClass().getName() : null;
  }


}
