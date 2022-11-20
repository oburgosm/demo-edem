/*
 * Copyright (c) 2021.  Inditex 
 */

package com.capgemini.edem.mongo;

import com.capgemini.edem.dgs.types.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author <a href="mailto:oscarbma@ext.inditex.com">Óscar Burgos Martín</a>
 * @since 
 */
@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{

}
