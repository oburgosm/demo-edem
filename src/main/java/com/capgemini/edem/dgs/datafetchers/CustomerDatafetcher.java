package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.types.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;

@DgsComponent
public class CustomerDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "customer"
  )
  public Customer getCustomer(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
