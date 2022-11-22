package com.capgemini.edem.mongo;

import com.capgemini.edem.mongo.dto.CustomerDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerDTO, String>  {

}
