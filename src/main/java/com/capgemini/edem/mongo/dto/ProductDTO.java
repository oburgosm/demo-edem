package com.capgemini.edem.mongo.dto;

import com.capgemini.edem.dgs.types.IProduct;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class ProductDTO implements IProduct {

  @Id
  private String id;
  
  @Indexed(unique = true)
  private String name;
  
  private String description;


  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  

}
