package com.chang.changsell.service;

import com.chang.changsell.dataObject.ProductCategory;

import java.util.List;

public interface CategoryService {

  ProductCategory findOne(Integer categoryId);

  List<ProductCategory> findAll();

  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

  ProductCategory save(ProductCategory productCategory);
}
