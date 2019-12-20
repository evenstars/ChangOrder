package com.chang.changsell.service;

import com.chang.changsell.dataObject.ProductInfo;
import com.chang.changsell.dto.CartDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
  ProductInfo findOne(String productId);

  /**
   * select all online products
   * @return
   */
  List<ProductInfo> findUpAll();

  Page<ProductInfo> findAll(Pageable pageable);

  ProductInfo save(ProductInfo productInfo);

  // add stock
  void increaseStock(List<CartDTO> cartDTOList);

  //decrease stock
  void decreaseStock(List<CartDTO> cartDTOList);
}
