package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.types.Order;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;

@DgsComponent
public class OrderDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "order"
  )
  public Order getOrder(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
