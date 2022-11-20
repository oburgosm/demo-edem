package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.types.Product;
import com.capgemini.edem.mongo.ProductRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@DgsComponent
public class ProductDatafetcher {
  
  private static final Logger LOG = LoggerFactory.getLogger(ProductDatafetcher.class);
  
  @Autowired
  private ProductRepository productRepository;
  
  @DgsData(
      parentType = QUERY.TYPE_NAME,
      field = QUERY.Product
  )
  public Mono<Product> getProduct(@InputArgument String id) {
    return this.productRepository.findById(id);
  }
}
