package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.types.IOrder;
import com.capgemini.edem.dgs.types.Order;
import com.capgemini.edem.dgs.types.OrderInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DgsComponent
public class OrderDatafetcher {

  @DgsQuery(field = QUERY.Order)
  public Mono<Order> getOrder(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }

  @DgsQuery(field = QUERY.Orders)
  public Flux<Order> getOrders(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
  
  @DgsMutation
  public Mono<? extends IOrder> createOrder(@InputArgument OrderInput orderInput) {
    return null;
  }
}
