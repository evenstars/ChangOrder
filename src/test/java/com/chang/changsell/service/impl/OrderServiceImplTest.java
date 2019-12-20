package com.chang.changsell.service.impl;

import com.chang.changsell.dataObject.OrderDetail;
import com.chang.changsell.dto.OrderDTO;
import com.chang.changsell.enums.OrderStatusEnum;
import com.chang.changsell.enums.PayStatusEnum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

  @Autowired
  private OrderServiceImpl orderService;

  private final String BUYEROPENID="110110";

  private final String ORDERID="1559227265705779500";

  @Test
  public void create() {
    OrderDTO orderDTO= new OrderDTO();
    orderDTO.setBuyerName("evenstar");
    orderDTO.setBuyerAddress("shanxi");
    orderDTO.setBuyerPhone("123456789012");
    orderDTO.setBuyerOpenid(BUYEROPENID);

    List<OrderDetail> orderDetailList = new ArrayList<>();
    OrderDetail o1=new OrderDetail();
    o1.setProductId("123456");
    o1.setProductQuantity(1);
    orderDetailList.add(o1);
    orderDTO.setOrderDetailList(orderDetailList);
    OrderDTO result=orderService.create(orderDTO);
    log.info("create order result={}",result);
  }

  @Test
  public void findOne() {
    OrderDTO result=orderService.findOne(ORDERID);
    log.info(" query single order ,result={}",result);
    Assert.assertEquals(ORDERID,result.getOrderId());
  }

  @Test
  public void findList() {
    PageRequest request = PageRequest.of(0,1);
    Page<OrderDTO> orderDTOPage = orderService.findList(BUYEROPENID,request);
    Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
  }

  @Test
  public void cancel() {
    OrderDTO orderDTO=orderService.findOne(ORDERID);
    OrderDTO result=orderService.cancel(orderDTO);
    Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
  }

  @Test
  public void finish() {
    OrderDTO orderDTO=orderService.findOne(ORDERID);
    OrderDTO result=orderService.finish(orderDTO);
    Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
  }

  @Test
  public void paid() {
    OrderDTO orderDTO=orderService.findOne(ORDERID);
    OrderDTO result=orderService.paid(orderDTO);
    Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getOrderStatus());
  }
}