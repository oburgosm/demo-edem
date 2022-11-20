package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.types.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@DgsComponent
public class CustomerDatafetcher {
  @DgsData(
      parentType = QUERY.TYPE_NAME,
      field = QUERY.Customer
  )
  public Mono<Customer> getCustomer(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
