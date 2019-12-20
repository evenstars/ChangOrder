package com.chang.changsell.service.impl;

import com.chang.changsell.dto.OrderDTO;
import com.chang.changsell.enums.ResultEnum;
import com.chang.changsell.exception.SellException;
import com.chang.changsell.service.BuyerService;
import com.chang.changsell.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

  @Autowired
  private OrderService orderService;

  @Override
  public OrderDTO findOrderOne(String openid, String orderId) {
    return checkOrderOwner(openid,orderId);
  }

  @Override
  public OrderDTO cancelOrder(String openid, String orderId) {
    OrderDTO orderDTO=checkOrderOwner(openid,orderId);
    if (orderDTO==null){
      log.error("[cancle order] can not query this order");
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    return orderService.cancel(orderDTO);
  }

  private OrderDTO checkOrderOwner(String openid, String orderId){
    OrderDTO orderDTO = orderService.findOne(orderId);
    if (orderDTO==null)
      return null;
    if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
      log.error("[query order] the openids are different");
      throw new SellException(ResultEnum.ORDER_MASTER_ERROR);
    }
    return orderDTO;
  }
}
