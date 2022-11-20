package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.types.Product;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;

@DgsComponent
public class ProductsDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "products"
  )
  public List<Product> getProducts(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
