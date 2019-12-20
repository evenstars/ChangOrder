package com.chang.changsell.controller;

import com.chang.changsell.VO.ResultVO;
import com.chang.changsell.converter.OrderForm2OrderDTOConverter;
import com.chang.changsell.dto.OrderDTO;
import com.chang.changsell.enums.ResultEnum;
import com.chang.changsell.exception.SellException;
import com.chang.changsell.form.OrderForm;
import com.chang.changsell.service.BuyerService;
import com.chang.changsell.service.OrderService;
import com.chang.changsell.utils.ResultVOUtil;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private BuyerService buyerService;

  // construct order
  @PostMapping("/create")
  public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      log.error("create order:arguments are error,orderForm={}",orderForm);
      throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
    }
    OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
    if (orderDTO.getOrderDetailList().isEmpty()){
      log.error("create order,cart can not be empty");
      throw new SellException(ResultEnum.CART_EMPTY);
    }
    OrderDTO createResult=orderService.create(orderDTO);
    Map<String,String> map=new HashMap<>();
    map.put("orderId",createResult.getOrderId());
    return ResultVOUtil.success(map);
  }

  //order list
  @GetMapping("/list")
  public ResultVO<List<OrderDTO>> list(@RequestParam("openid")String openid,
                                       @RequestParam(value = "page",defaultValue = "0")Integer page,
                                       @RequestParam(value = "size",defaultValue = "10")Integer size){
    if (openid.isEmpty()){
      log.error("[query order list] openid is empty");
      throw new SellException(ResultEnum.PARAM_ERROR);
    }
    PageRequest request=PageRequest.of(page,size);
    Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);

    return ResultVOUtil.success(orderDTOPage.getContent());

  }

  //order detail
  @GetMapping("/detail")
  public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                   @RequestParam("orderId")String orderId){
    OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
    return ResultVOUtil.success(orderDTO);
  }

  //cancel order
  @PostMapping("cancle")
  public ResultVO cancel(@RequestParam("openid") String openid,
                         @RequestParam("orderId")String orderId){
    buyerService.cancelOrder(openid,orderId);
    return ResultVOUtil.success();
  }

}
