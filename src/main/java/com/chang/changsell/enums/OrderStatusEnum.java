package com.chang.changsell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
  NEW(0,"new order"),
  FINISHED(1,"finished"),
  CANCEL(2,"canceled order");

  private Integer code;

  private String message;

  OrderStatusEnum(Integer code,String message){
    this.code=code;
    this.message = message;
  }
}
