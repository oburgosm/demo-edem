package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.types.Order;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;

@DgsComponent
public class OrdersDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "orders"
  )
  public List<Order> getOrders(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
