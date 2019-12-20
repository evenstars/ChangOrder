package com.chang.changsell.service.impl;

import com.chang.changsell.converter.OrderMaster2OrderDTOConverter;
import com.chang.changsell.dataObject.OrderDetail;
import com.chang.changsell.dataObject.OrderMaster;
import com.chang.changsell.dataObject.ProductInfo;
import com.chang.changsell.dto.CartDTO;
import com.chang.changsell.dto.OrderDTO;
import com.chang.changsell.enums.OrderStatusEnum;
import com.chang.changsell.enums.PayStatusEnum;
import com.chang.changsell.enums.ResultEnum;
import com.chang.changsell.exception.SellException;
import com.chang.changsell.repository.OrderDetailRepository;
import com.chang.changsell.repository.OrderMasterRepository;
import com.chang.changsell.service.OrderService;
import com.chang.changsell.service.ProductService;
import com.chang.changsell.utils.KeyUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.text.CollatorUtilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  private ProductService productService;

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Autowired
  private OrderMasterRepository orderMasterRepository;

  @Override
  @Transactional
  public OrderDTO create(OrderDTO orderDTO) {
    String orderId=KeyUtil.genUniqueKey();
    BigDecimal orderAmount = new BigDecimal(0);
//    List<CartDTO> cartDTOList = new ArrayList<>();

    //1.query product(quantity,price)
    for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
      ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
      if (productInfo==null){
        throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
      }

      //2.calculate total price
      orderAmount=productInfo.getProductPrice()
              .multiply(new BigDecimal(orderDetail.getProductQuantity()))
              .add(orderAmount);

      orderDetail.setDetailId(KeyUtil.genUniqueKey());
      orderDetail.setOrderId(orderId);
      BeanUtils.copyProperties(productInfo,orderDetail);
      orderDetailRepository.save(orderDetail);

//      CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//      cartDTOList.add(cartDTO);
    }
    //3.write to order database(orderMaster,orderDetail)
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(orderDTO,orderMaster);
    orderMaster.setOrderId(orderId);
    orderMaster.setOrderAmount(orderAmount);
    orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
    orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
    orderMasterRepository.save(orderMaster);

    //4.if successful,decrease stock
    List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
      new CartDTO(e.getProductId(),e.getProductQuantity())
    ).collect(Collectors.toList());

    productService.decreaseStock(cartDTOList);
    return orderDTO;
  }

  @Override
  public OrderDTO findOne(String orderId) {
    OrderMaster orderMaster=orderMasterRepository.findById(orderId).orElse(null);
    if (orderMaster == null)
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
    if (orderDetailList.isEmpty())
      throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
    OrderDTO orderDTO = new OrderDTO();
    BeanUtils.copyProperties(orderMaster,orderDTO);
    orderDTO.setOrderDetailList(orderDetailList);

    return orderDTO;
  }

  @Override
  public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
    Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
    List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
    Page<OrderDTO> orderDTOPage=new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    return orderDTOPage;
  }

  @Override
  @Transactional
  public OrderDTO cancel(OrderDTO orderDTO) {
    OrderMaster orderMaster=new OrderMaster();

    //determine the status of the order
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
      log.error("cancel order:order status error,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }
    //modify the status of the order
    orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
    BeanUtils.copyProperties(orderDTO,orderMaster);
    OrderMaster updateResult=orderMasterRepository.save(orderMaster);
    if (updateResult == null){
      log.error("cancel order error,orderMaster = {}",orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }
    //return the stock information
    if (orderDTO.getOrderDetailList().isEmpty()){
      log.error("cancle order:no production details,orderDTO={}",orderDTO);
      throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
    }
    List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
            .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
            .collect(Collectors.toList());
    productService.increaseStock(cartDTOList);
    //return the money, if the order is paid
    if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
      //TODO
    }
    return orderDTO;
  }

  @Override
  @Transactional
  public OrderDTO finish(OrderDTO orderDTO) {
    //determine the status of the order
    if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
      log.error("order finishing: the status of the order is incorrect,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    //update status
    orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
    OrderMaster orderMaster=new OrderMaster();
    BeanUtils.copyProperties(orderDTO,orderMaster);
    OrderMaster updateResult=orderMasterRepository.save(orderMaster);
    if (updateResult == null){
      log.error("finishing order error,orderMaster = {}",orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }
    return orderDTO;
  }

  @Override
  @Transactional
  public OrderDTO paid(OrderDTO orderDTO) {
    //determine the status
    if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
      log.error("order paid: the status of the order is incorrect,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    //determine the paid status
    if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
      log.error("order paid status incorrect,orderDTO={}",orderDTO);
      throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
    }

    //modify the status of the paid
    orderDTO.setOrderStatus(PayStatusEnum.SUCCESS.getCode());
    OrderMaster orderMaster=new OrderMaster();
    BeanUtils.copyProperties(orderDTO,orderMaster);
    OrderMaster updateResult=orderMasterRepository.save(orderMaster);
    if (updateResult == null){
      log.error("pay the  order error,orderMaster = {}",orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }
    return orderDTO;
  }
}
