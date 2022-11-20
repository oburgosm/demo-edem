package com.capgemini.edem.dgs.mutation;

import com.capgemini.edem.dgs.DgsConstants.MUTATION;
import com.capgemini.edem.dgs.types.Product;
import com.capgemini.edem.mongo.ProductRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

/**
 *
 */
@DgsComponent
public class ProductMutation {
  
  @Autowired
  private ProductRepository productRepository;


  @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.CreateProduct)
  public Mono<Product> createProduct(@InputArgument String name) {
    return this.productRepository.save(Product.newBuilder().name(name).build());
  }
}