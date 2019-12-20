package com.chang.changsell.controller;

import com.chang.changsell.VO.ProductInfoVO;
import com.chang.changsell.VO.ProductVO;
import com.chang.changsell.VO.ResultVO;
import com.chang.changsell.dataObject.ProductCategory;
import com.chang.changsell.dataObject.ProductInfo;
import com.chang.changsell.service.CategoryService;
import com.chang.changsell.service.ProductService;
import com.chang.changsell.utils.ResultVOUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * buyer product
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/list")
  public ResultVO list(){
    //1.find all online product
    List<ProductInfo> productInfoList=productService.findUpAll();
    //2.select category (select all at one time)
    List<Integer> categoryTypeList = new ArrayList<>();
    for (ProductInfo productInfo:productInfoList)
      categoryTypeList.add(productInfo.getCategoryType());
    List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);
    //3.concat data
    List<ProductVO> productVOList = new ArrayList<>();
    for (ProductCategory productCategory:productCategoryList){
      ProductVO productVO=new ProductVO();
      productVO.setCategoryType(productCategory.getCategoryType());
      productVO.setCategoryName(productCategory.getCategoryName());

      List<ProductInfoVO> productInfoVOList=new ArrayList<>();
      for (ProductInfo productInfo:productInfoList){
        if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
          ProductInfoVO productInfoVO=new ProductInfoVO();
          BeanUtils.copyProperties(productInfo,productInfoVO);
          productInfoVOList.add(productInfoVO);
        }
      }
      productVO.setProductInfoVOList(productInfoVOList);
      productVOList.add(productVO);
    }
    return ResultVOUtil.success(productVOList);
  }
}
