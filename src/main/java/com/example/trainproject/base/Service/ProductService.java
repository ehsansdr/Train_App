  package com.example.trainproject.base.Service;

  import co.elastic.clients.elasticsearch.ElasticsearchClient;
  import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
  import co.elastic.clients.elasticsearch.core.SearchRequest;
  import co.elastic.clients.elasticsearch.core.SearchResponse;
  import co.elastic.clients.elasticsearch.core.search.Hit;
  import com.example.trainproject.base.Model.Product;
  import com.example.trainproject.base.Repository.ProductRepository;
  import java.io.IOException;
  import java.util.List;
  import java.util.stream.Collectors;
  import lombok.RequiredArgsConstructor;
  import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ElasticsearchClient elasticsearchClient;


  public double getAveragePriceElasticsearchClient() throws IOException {
    SearchResponse<Product> searchResponse = elasticsearchClient.search(
        SearchRequest.of( s -> s.index("products")
            .size(0) // I don't want documents, just aggregations
            .aggregations("average_price", //  the name of aggregation
                a -> a.avg(avg -> avg.field("price")) // "avg" = I want average of field "price"
            )
        ),
    Product.class
    );
    /*
    {
      "size": 0,
      "aggs": {
        "average_price": {
          "avg": {
            "field": "price"
          }
        }
      }
    }
     */



    Aggregate averagePriceAgo = searchResponse.aggregations().get("average_price");
    /*
    {
      "took": 12,
      "timed_out": false,
      "_shards": { ... },
      "hits": {
        "total": { "value": 3, "relation": "eq" },
        "hits": []
      },
      "aggregations": {
        "average_price": {
          "value": 1199.99
        }
      }
    }

     */


    // Always check if aggregation result is null
    // Always check if it's the right type (isAvg(), isSum(), isTerms())
    if (averagePriceAgo != null && averagePriceAgo.isAvg()) {
      return averagePriceAgo.avg().value();
//      "aggregations": {
//        "average_price": {
//          "value": 1199.99
//        }

    }
    return 0.0;
  }

  public List<Product> getProductElasticsearchClientWithHits() throws IOException {
    SearchResponse<Product> search = elasticsearchClient.search(
        s -> s.index("products")
            .query(q -> q.matchAll(m -> m)),
        Product.class
    );

    return search.hits().hits().stream()
        .map(Hit::source)
        .collect(Collectors.toList());
  }


  public Product save(Product product) {
    return productRepository.save(product);
  }

  public Product findByName(String name) {
    return productRepository.findByName(name).orElse(null);
  }

  public Iterable<Product> findAll() {
    return productRepository.findAll();
  }
}
