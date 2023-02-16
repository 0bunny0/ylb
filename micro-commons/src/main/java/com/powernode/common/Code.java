package com.powernode.common;

/**
 * 统一返回类型的 枚举值
 */
public enum Code {
    /*根据响应场景 添加枚举类型*/
    SUCCESS(1000,"请求成功"),
    FAILURE(9999,"请求失败,稍后重试")
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
