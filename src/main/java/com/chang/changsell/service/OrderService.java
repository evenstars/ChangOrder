package com.chang.changsell.service;

import com.chang.changsell.dataObject.OrderMaster;
import com.chang.changsell.dto.OrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  //construct order
  OrderDTO create(OrderDTO orderDTO);

  //query order
  OrderDTO findOne(String orderId);

  Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

  OrderDTO cancel(OrderDTO orderDTO);

  OrderDTO finish(OrderDTO orderDTO);

  OrderDTO paid(OrderDTO orderDTO);
}
