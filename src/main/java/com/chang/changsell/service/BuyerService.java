package com.chang.changsell.service;

import com.chang.changsell.dto.OrderDTO;

public interface BuyerService {

  //query order
  OrderDTO findOrderOne(String openid,String orderId);

  //cancel order
  OrderDTO cancelOrder(String openid,String orderId);
}
