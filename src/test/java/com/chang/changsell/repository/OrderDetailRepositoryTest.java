package com.chang.changsell.repository;

import com.chang.changsell.dataObject.OrderDetail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

  @Autowired
  private OrderDetailRepository repository;

  @Test
  public void saveTest(){
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setDetailId("1234567810");
    orderDetail.setOrderId("111111");
    orderDetail.setProductIcon("http://ddd.jpg");
    orderDetail.setProductId("123456");
    orderDetail.setProductName("nudle");
    orderDetail.setProductPrice(new BigDecimal(2.5));
    orderDetail.setProductQuantity(2);
    OrderDetail result =repository.save(orderDetail);
    Assert.assertNotNull(result);
  }

  @Test
  public void findByOrderId() {
    List<OrderDetail> orderDetailList=repository.findByOrderId("111111");
    Assert.assertNotEquals(0,orderDetailList.size());
  }
}