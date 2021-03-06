package com.chang.changsell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
  PRODUCT_NOT_EXIST(10,"NO SUCH A PRODUCT"),
  PRODUCT_STOCK_ERROR(11,"NOT ENOUGH PRODUCT"),
  ORDER_NOT_EXIST(12,"NO SUCH ORDER"),
  ORDER_DETAIL_NOT_EXIST(13,"ORDER DETAIL NOT EXIST"),
  ORDER_STATUS_ERROR(14,"ORDER STATUS ERROR"),
  ORDER_UPDATE_FAIL(15,"ORDER UPDATE FAILS"),
  ORDER_DETAIL_EMPTY(16,"ORDER DETAIL IS EMPTY"),
  ORDER_PAY_STATUS_ERROR(17,"ORDER PAYMENT STATUS INCORRECT"),
  PARAM_ERROR(1,"PARAMETER ERROR"),
  CART_EMPTY(18,"CART IS EMPTY"),
  ORDER_MASTER_ERROR(19,"THE ORDER DOES NOT BELONG TO CURRENT USSER");

  private Integer code;

  private String message;

  ResultEnum(Integer code,String message){
    this.code=code;
    this.message = message;
  }

}
