package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.types.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

@DgsComponent
public class CustomersDatafetcher {
  @DgsData(
      parentType = QUERY.TYPE_NAME,
      field = QUERY.Customers
  )
  public Flux<Customer> getCustomers(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
