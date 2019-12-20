package com.chang.changsell.dataObject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * product
 */
@Entity
@Data
public class ProductInfo {

  @Id
  private String productId;

  private String productName;

  private BigDecimal productPrice;

  private Integer productStock;

  private String productDescription;

  private String productIcon;

  /**
   * status,1:normal 2:off
   */
  private Integer productStatus;

  private Integer categoryType;
}
