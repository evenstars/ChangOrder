package com.chang.changsell.exception;

import com.chang.changsell.enums.ResultEnum;

public class SellException extends RuntimeException{

  private Integer code;

  public SellException(ResultEnum resultEnum){
    super(resultEnum.getMessage());
    this.code=resultEnum.getCode();
  }

  public SellException(Integer code,String message){
    super(message);
    this.code=code;
  }
}
