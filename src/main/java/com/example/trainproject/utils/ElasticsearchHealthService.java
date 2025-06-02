package com.example.trainproject.utils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticsearchHealthService {

  private final ElasticsearchOperations elasticsearchOperations;

  public ElasticsearchHealthService(ElasticsearchOperations elasticsearchOperations) {
    this.elasticsearchOperations = elasticsearchOperations;
  }

  @PostConstruct
  public void testConnection() {
    try {
      boolean health = elasticsearchOperations.indexOps(IndexCoordinates.of("test")).exists();
      log.info("✅ Successfully connected to Elasticsearch");
    } catch (Exception e) {
      log.error("❌ Failed to connect to Elasticsearch: {}", e.getMessage());
    }
  }
}

