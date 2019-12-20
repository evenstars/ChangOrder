package com.chang.changsell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.chang.changsell.dataObject.OrderDetail;
import com.chang.changsell.dto.OrderDTO;
import com.chang.changsell.enums.ResultEnum;
import com.chang.changsell.exception.SellException;
import com.chang.changsell.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderForm2OrderDTOConverter {
  public static OrderDTO convert(OrderForm orderForm){
    Gson gson=new Gson();
    OrderDTO orderDTO = new OrderDTO();

    orderDTO.setBuyerName(orderForm.getName());
    orderDTO.setBuyerPhone(orderForm.getPhone());
    orderDTO.setBuyerAddress(orderForm.getAddress());
    orderDTO.setBuyerOpenid(orderForm.getOpenid());
    List<OrderDetail> orderDetailList;
    try{
      orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
    }catch (Exception e){
      log.error("convert object:error,string={}",orderForm.getItems());
      throw new SellException(ResultEnum.PARAM_ERROR);
    }
    orderDTO.setOrderDetailList(orderDetailList);
    return orderDTO;
  }
}
