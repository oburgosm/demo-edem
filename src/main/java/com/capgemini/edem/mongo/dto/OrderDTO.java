package com.capgemini.edem.mongo.dto;

import java.util.List;

import com.capgemini.edem.dgs.types.IOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class OrderDTO implements IOrder {
  
  @Id
  private String id;
  private List<ProductOrderDTO> products;
  private CustomerDTO customer;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<ProductOrderDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductOrderDTO> products) {
    this.products = products;
  }

  public CustomerDTO getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDTO customer) {
    this.customer = customer;
  }

  

}
