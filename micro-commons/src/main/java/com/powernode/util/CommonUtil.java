package com.powernode.util;

import java.util.regex.Pattern;

/**
 * 公共工具类
 */
public class CommonUtil {
    /*验证码格式*/
    public static boolean checkPhone(String phone) {
        if (phone == null || "".equals(phone)) {
            return false;
        }
        /*使用正则表达式验证手机号码*/
        return Pattern.matches("^1[1-9]\\d{9}$", phone);
    }

    /*分页页码校验*/
    public static Integer pageNo(Integer pageNo){
        if(pageNo == null || pageNo.intValue() < 1){
            pageNo = 1;
        }
        return pageNo;
    }

    public static Integer pageSize(Integer pageSize) {
        if(pageSize == null || pageSize.intValue() < 1){
            pageSize = 5;
        }
        return pageSize;
    }
}
