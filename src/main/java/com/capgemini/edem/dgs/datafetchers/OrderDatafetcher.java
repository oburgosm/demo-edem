package com.capgemini.edem.dgs.datafetchers;

import java.util.List;

import com.capgemini.edem.dgs.DgsConstants.QUERY;
import com.capgemini.edem.dgs.mapper.TypeMapper;
import com.capgemini.edem.dgs.types.IOrder;
import com.capgemini.edem.dgs.types.IProductOrder;
import com.capgemini.edem.dgs.types.Order;
import com.capgemini.edem.dgs.types.OrderInput;
import com.capgemini.edem.dgs.types.ProductOrder;
import com.capgemini.edem.mongo.CustomerRepository;
import com.capgemini.edem.mongo.OrderRepository;
import com.capgemini.edem.mongo.ProductRepository;
import com.capgemini.edem.mongo.dto.OrderDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@DgsComponent
public class OrderDatafetcher {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private TypeMapper mapper;

  @DgsQuery(field = QUERY.Order)
  public Mono<? extends IOrder> getOrder(@InputArgument String id) {
    return this.orderRepository.findById(id);
  }

  @DgsQuery(field = QUERY.Orders)
  public Flux<? extends IOrder> getOrders(DataFetchingEnvironment dataFetchingEnvironment) {
    return this.orderRepository.findAll();
  }

  @DgsMutation
  public Mono<? extends IOrder> createOrder(@InputArgument OrderInput order) {
    Order.Builder builder = Order.newBuilder();
    return Flux.fromStream(order.getProducts().stream())
            .parallel()
            .runOn(Schedulers.boundedElastic())
            .flatMap((poi) -> {
              return this.productRepository.findById(poi.getProduct().getId()).map((p) -> {
                return ProductOrder.newBuilder().product(p).quantity(poi.getQuantity()).build();
              });
            }).sequential()
            .collectList()
            .doOnNext((t) -> {
              builder.products((List<IProductOrder>) (List<?>) t);
            })
            .flatMap((t) -> {
              return this.customerRepository.findById(order.getCustomer().getId());
            })
            .doOnNext((t) -> {
              builder.customer(t);
            })
            .flatMap((t) -> {
              OrderDTO orderDTO = this.mapper.toOrderDTO(builder.build());
              return this.orderRepository.save(orderDTO);
            });
  }

  @DgsMutation
  public Mono<? extends IOrder> deleteOrder(@InputArgument String id) {
    return this.orderRepository.findById(id).doOnNext(this::deleteOrder);
  }

  private void deleteOrder(OrderDTO c) {
    this.orderRepository.delete(c).subscribe();
  }
}
