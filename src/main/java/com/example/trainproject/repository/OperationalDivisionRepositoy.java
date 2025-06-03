package com.example.trainproject.repository;

import com.example.trainproject.Model.OperationalDivision;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalDivisionRepositoy extends
    ElasticsearchRepository<OperationalDivision, String> {

}
