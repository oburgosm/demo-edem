package com.capgemini.edem.dgs.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.capgemini.edem.dgs.types.Customer;
import com.capgemini.edem.dgs.types.Order;
import com.capgemini.edem.dgs.types.Product;
import com.capgemini.edem.dgs.types.ProductOrder;
import com.capgemini.edem.mongo.dto.CustomerDTO;
import com.capgemini.edem.mongo.dto.OrderDTO;
import com.capgemini.edem.mongo.dto.ProductDTO;
import com.capgemini.edem.mongo.dto.ProductOrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TypeMapper {
  
  ProductDTO toProductDTO(Product product);
  Product toProduct(ProductDTO product);
  
  CustomerDTO toCustomerDTO(Customer customer);
  Customer toCustomer(CustomerDTO customerDTO);
  
  ProductOrderDTO toProductOrderDTO(ProductOrder productOrder);
  ProductOrder toProductOrdet(ProductOrderDTO productOrder);
  
  OrderDTO toOrderDTO(Order order);
  Order toOrder(OrderDTO order);

}
