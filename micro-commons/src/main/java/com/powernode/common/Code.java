package com.powernode.common;

/**
 * 统一返回类型的 枚举值
 */
public enum Code {
    /*根据响应场景 添加枚举类型*/
    SUCCESS(1000,"请求成功"),
    FAILURE(9999,"请求失败,稍后重试"),
    PHONE_FORMAT_ERROR(1001,"手机格式错误"),
    PHONE_HAD_REGISTER(1002,"手机号码已被注册"),
    QUERY_PARAM_ERROR(1003,"参数错误"),
    SMS_CODE_EXISTS(1004,"验证码可以使用,无需再次获取"),
    SMS_SEND_ERROR(1005,"短信发送失败"),
    SMS_CODE_INVALIDATE(1006,"验证码输入错误"),
    SMS_LOGIN_ERROR(1007,"账号密码错误"),
    TOKEN_INVALIDATE(4003,"访问失败")
    ;

    Code(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    private Integer code;
    private String text;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
