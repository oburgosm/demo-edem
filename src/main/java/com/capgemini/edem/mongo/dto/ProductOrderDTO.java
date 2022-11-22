package com.capgemini.edem.mongo.dto;

import com.capgemini.edem.dgs.types.IProductOrder;

public class ProductOrderDTO implements IProductOrder {
  
  private ProductDTO product;
  private int quantity;

  @Override
  public ProductDTO getProduct() {
    return product;
  }

  public void setProduct(ProductDTO product) {
    this.product = product;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  

}
