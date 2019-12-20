package com.chang.changsell.service.impl;

import com.chang.changsell.dataObject.ProductInfo;
import com.chang.changsell.enums.ProductStatusEnum;

import org.apache.tomcat.jni.Proc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

  @Autowired
  private ProductServiceImpl productService;

  @Test
  public void findOne() {
    ProductInfo productInfo= productService.findOne("123456");
    Assert.assertEquals("123456",productInfo.getProductId());
  }

  @Test
  public void findUpAll() {
    List<ProductInfo> productInfoList=productService.findUpAll();
    Assert.assertNotEquals(0,productInfoList.size());
  }

  @Test
  public void findAll() {
    PageRequest request=PageRequest.of(0,2);
    Page<ProductInfo> productInfoPage=productService.findAll(request);
    System.out.println(productInfoPage.getTotalElements());
  }

  @Test
  public void save() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setProductId("123457");
    productInfo.setProductName("pizza");
    productInfo.setProductPrice(new BigDecimal(8.2));
    productInfo.setProductStock(100);
    productInfo.setProductDescription("nice");
    productInfo.setProductIcon("http://ddd.com");
    productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
    productInfo.setCategoryType(2);
    ProductInfo result=productService.save(productInfo);
    Assert.assertNotNull(result);
  }
}