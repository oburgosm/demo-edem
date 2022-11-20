package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.types.Product;
import com.capgemini.edem.mongo.ProductRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@DgsComponent
public class ProductsDatafetcher {
  
  @Autowired
  private ProductRepository productRepository;
  
  @DgsData(
      parentType = QUERY.TYPE_NAME,
      field = QUERY.Products
  )
  public Flux<Product> getProducts(DataFetchingEnvironment dataFetchingEnvironment) {
    return this.productRepository.findAll();
  }
}
