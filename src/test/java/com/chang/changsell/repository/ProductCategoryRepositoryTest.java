package com.chang.changsell.repository;

import com.chang.changsell.dataObject.ProductCategory;

import org.apache.tomcat.jni.Proc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
  @Autowired
  private ProductCategoryRepository repository;

  @Test
  public void findOneTest(){
    ProductCategory productCategory=repository.findById(1).orElse(null);
    System.out.println(productCategory.toString());
  }

  @Test
  @Transactional
  public void saveTest(){
    ProductCategory productCategory=new ProductCategory("女生最爱",5);
    ProductCategory result=repository.save(productCategory);
    Assert.assertNotNull(result);
  }

  @Test
  public void findByCategoryTypeInTest(){
    List<Integer> list= Arrays.asList(1,10,2);
    List<ProductCategory> result=repository.findByCategoryTypeIn(list);
    Assert.assertEquals(0,result.size());
  }
}