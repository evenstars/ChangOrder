package com.chang.changsell.repository;

import com.chang.changsell.dataObject.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

  List<OrderDetail> findByOrderId(String orderId);
}
