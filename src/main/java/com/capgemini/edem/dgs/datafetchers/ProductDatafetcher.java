package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.mapper.TypeMapper;
import com.capgemini.edem.dgs.types.IProduct;
import com.capgemini.edem.dgs.types.Product;
import com.capgemini.edem.mongo.ProductRepository;
import com.capgemini.edem.mongo.dto.ProductDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DgsComponent
public class ProductDatafetcher {

  @Autowired
  private ProductRepository productRepository;

  @DgsQuery(field = QUERY.Product)
  public Mono<? extends IProduct> getProduct(@InputArgument String id) {
    return this.productRepository.findById(id);
  }

  @DgsQuery(field = QUERY.Products)
  public Flux<? extends IProduct> getProducts(DataFetchingEnvironment dataFetchingEnvironment) {
    return this.productRepository.findAll();
  }

  @Autowired
  private TypeMapper mapper;

  @DgsMutation
  public Mono<? extends IProduct> createProduct(@InputArgument String name) {
    return this.productRepository.save(this.mapper.toProductDTO(Product.newBuilder().name(name).build()));
  }

  @DgsMutation
  public Mono<? extends IProduct> deleteProduct(@InputArgument String id) {
    return this.productRepository.findById(id).doOnNext(this::deleteProduct);
  }

  @DgsMutation
  public Mono<? extends IProduct> updateProduct(@InputArgument String id, @InputArgument String name) {
    return this.productRepository.findById(id).flatMap((p) -> {
      p.setName(name);
      return this.productRepository.save(p);
    });
  }

  private void deleteProduct(ProductDTO p) {
    this.productRepository.delete(p).subscribe();
  }
}
