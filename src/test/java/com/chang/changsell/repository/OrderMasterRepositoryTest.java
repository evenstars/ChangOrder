package com.chang.changsell.repository;

import com.chang.changsell.dataObject.OrderMaster;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import javax.persistence.Entity;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

  @Autowired
  private OrderMasterRepository repository;

  private final String OPENID="110110";

  @Test
  public void findByBuyerOpenid() {
    PageRequest request = PageRequest.of(0,1);
    Page<OrderMaster> result=repository.findByBuyerOpenid(OPENID,request);
    Assert.assertNotEquals(0,result.getTotalElements());
  }

  @Test
  public void saveTest(){
    OrderMaster orderMaster = new OrderMaster();
    orderMaster.setOrderId("123456");
    orderMaster.setBuyerName("baba");
    orderMaster.setBuyerPhone("123456789123");
    orderMaster.setBuyerAddress("shanxi");
    orderMaster.setBuyerOpenid("110110");
    orderMaster.setOrderAmount(new BigDecimal(2.3));
    OrderMaster result=repository.save(orderMaster);
    Assert.assertNotNull(result);
  }
}