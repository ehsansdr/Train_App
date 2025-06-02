package com.example.trainproject.Model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "referral")
public class Referral {

}
