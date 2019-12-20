package com.chang.changsell.dataObject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class OrderDetail {

  @Id
  private String detailId;

  private String orderId;

  private String productId;

  private String productName;

  private BigDecimal productPrice;

  private Integer productQuantity;

  private String productIcon;
}
