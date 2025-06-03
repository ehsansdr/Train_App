package com.example.trainproject.callBack;

import java.time.ZonedDateTime;

/**
 * Any document that has createdAt/updatedAt
 */
public interface Auditable {
  ZonedDateTime getCreatedAt();
  void setCreatedAt(ZonedDateTime createdAt);

  ZonedDateTime getUpdatedAt();
  void setUpdatedAt(ZonedDateTime updatedAt);

  ZonedDateTime getDeletedAt();
  void setDeletedAt(ZonedDateTime deletedAt);
}


