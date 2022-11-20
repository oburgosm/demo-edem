package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.types.Order;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@DgsComponent
public class OrderDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "order"
  )
  public Mono<Order> getOrder(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
