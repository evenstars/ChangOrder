package com.chang.changsell.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OrderForm {

  @NotEmpty(message = "name is necessary")
  private String name;

  @NotEmpty(message = "phone is necessary")
  private String phone;

  @NotEmpty(message = "address is necessary")
  private String address;

  @NotEmpty(message = "openid is necessary")
  private String openid;

  @NotEmpty(message = "shopping list is necessary")
  private String items;

}
