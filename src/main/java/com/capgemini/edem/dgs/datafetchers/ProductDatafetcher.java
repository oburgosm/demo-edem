package com.capgemini.edem.dgs.datafetchers;

import com.capgemini.edem.dgs.types.Product;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;

@DgsComponent
public class ProductDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "product"
  )
  public Product getProduct(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
