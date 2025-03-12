package com.example.trainproject.base.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GenericPaginatedResponse<T> {


  private T items;
  private Meta meta;

  public static <T> GenericPaginatedResponse<T> success(
      T content,
      int currentPage,
      int pageSize,
      Long total
  ) {
    return new GenericPaginatedResponse<>(
        pageSize, currentPage, total, content
    );
  }

  public GenericPaginatedResponse(
      int pageSize,
      int currentPage,
      Long total,
      T content
  ) {
    this.meta = new Meta(pageSize, currentPage, total);
    this.items = content;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Meta {
    private int pageSize;
    private int currentPage;
    private Long total;
  }
}