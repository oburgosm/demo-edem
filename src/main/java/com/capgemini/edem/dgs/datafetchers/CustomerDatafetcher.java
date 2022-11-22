package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.mapper.TypeMapper;
import com.capgemini.edem.dgs.types.Customer;
import com.capgemini.edem.dgs.types.ICustomer;
import com.capgemini.edem.mongo.CustomerRepository;
import com.capgemini.edem.mongo.dto.CustomerDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DgsComponent
public class CustomerDatafetcher {
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @Autowired
  private TypeMapper mapper;
  
  @DgsQuery(field = QUERY.Customer)
  public Mono<? extends ICustomer> getCustomer(@InputArgument String id) {
    return this.customerRepository.findById(id);
  }
  
  @DgsQuery(field = QUERY.Customers)
  public Flux<? extends ICustomer> getCustomers(DataFetchingEnvironment dataFetchingEnvironment) {
    return this.customerRepository.findAll();
  }
  
  @DgsMutation
  public Mono<? extends ICustomer> createCustomer(@InputArgument String firstName, @InputArgument String surName) {
    return this.customerRepository.save(this.mapper.toCustomerDTO(Customer.newBuilder().firstName(firstName).surName(surName).build()));
  }

  @DgsMutation
  public Mono<? extends ICustomer> deleteCustomer(@InputArgument String id) {
    return this.customerRepository.findById(id).doOnNext(this::deleteCustomer);
  }

  @DgsMutation
  public Mono<? extends ICustomer> updateCustomer(@InputArgument String id, @InputArgument String firstName, @InputArgument String surName) {
    return this.customerRepository.findById(id).flatMap((c) -> {
      c.setFirstName(firstName);
      c.setSurName(surName);
      return this.customerRepository.save(c);
    });
  }

  private void deleteCustomer(CustomerDTO c) {
    this.customerRepository.delete(c).subscribe();
  }
}
