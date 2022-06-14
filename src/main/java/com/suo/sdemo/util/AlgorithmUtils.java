 package com.suo.sdemo.util;

import org.springframework.util.DigestUtils;

/**
  * 算法工具类
 * @author 锁战强
 * @date 2022/03/09
 */
public class AlgorithmUtils {
    
    public static String md5(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }
}
