package com.capgemini.edem.mongo;

import com.capgemini.edem.mongo.dto.ProductDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductDTO, String>{

}
