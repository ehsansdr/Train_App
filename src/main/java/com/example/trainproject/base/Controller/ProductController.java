package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Dto.GenericPaginatedResponse;
import com.example.trainproject.base.Model.Product;
import com.example.trainproject.base.Repository.ProductRepository;
import com.example.trainproject.base.Service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

  private final ProductService productService;

  @PostMapping
  public Product createProduct(Product product){
    return productService.save(product);
  }

  @GetMapping("/search")
  public GenericPaginatedResponse<Iterable<Product>> findAll(
      int pageSize,
      int pageNumber
  ){
    List<Product> products = (List<Product>) productService.findAll();
    return GenericPaginatedResponse.success(products,
        pageNumber,
        pageSize,
        (long) products.size()
    );
  }
}
