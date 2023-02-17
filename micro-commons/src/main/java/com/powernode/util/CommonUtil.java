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
}
