package com.chang.changsell.dto;

import lombok.Data;

@Data
public class CartDTO {
  private String productid;

  private Integer productQuantity;

  public CartDTO(String productid,Integer productQuantity) {
    this.productid = productid;
    this.productQuantity=productQuantity;
  }
}
