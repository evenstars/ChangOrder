package com.chang.changsell.dataObject;

import com.chang.changsell.enums.OrderStatusEnum;
import com.chang.changsell.enums.PayStatusEnum;

import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

  //order id
  @Id
  private String orderId;

  private String buyerName;

  private String buyerPhone;

  private String buyerAddress;

  private String buyerOpenid;

  private BigDecimal orderAmount;

  private Integer orderStatus = OrderStatusEnum.NEW.getCode();

  private Integer payStatus = PayStatusEnum.WAIT.getCode();

  private Date createTime;

  private Date updateTime;
}
