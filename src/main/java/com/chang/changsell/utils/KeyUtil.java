package com.chang.changsell.utils;

import java.util.Random;

public class KeyUtil {

  /**
   * generate unique main key
   * format:time+random number
   * @return
   */
  public static synchronized String genUniqueKey(){
    Random random = new Random();
    Integer num=random.nextInt(900000)+100000;
    return System.currentTimeMillis()+String.valueOf(num);
  }
}
