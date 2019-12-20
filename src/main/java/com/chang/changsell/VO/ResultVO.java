package com.chang.changsell.VO;

import lombok.Data;

/**
 * http request return object
 */
@Data
public class ResultVO<T> {

  /**
   * 错误码
   */
  private Integer code;

  /**
   * hint information
   */
  private String msg;

  /**
   * returned content
   */
  private T data;
}
