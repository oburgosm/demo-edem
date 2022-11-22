package com.capgemini.edem.mongo;

import com.capgemini.edem.mongo.dto.OrderDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderDTO, String>{

}
