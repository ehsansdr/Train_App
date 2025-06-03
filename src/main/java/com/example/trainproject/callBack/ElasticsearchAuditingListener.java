package com.example.trainproject.callBack;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import org.springframework.data.elasticsearch.core.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchAuditingListener implements BeforeConvertCallback<Auditable> {

  @Override
  public Auditable onBeforeConvert(Auditable entity, org.springframework.data.elasticsearch.core.mapping.IndexCoordinates index) {
    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(now);
    }
    entity.setUpdatedAt(now);
    return entity;
  }
}

