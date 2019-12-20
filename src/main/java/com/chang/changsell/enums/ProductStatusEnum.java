package com.chang.changsell.enums;

import lombok.Getter;

/**
 * product status
 */
@Getter
public enum ProductStatusEnum {

  UP(0,"online product"),
  DOWN(1,"offline product");
  private Integer code;

  private String message;

  ProductStatusEnum(Integer code,String message){
    this.code=code;
    this.message=message;
  }
}
