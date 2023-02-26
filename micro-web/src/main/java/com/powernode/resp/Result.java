package com.powernode.resp;

import com.powernode.common.Code;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 统一返回类型
 */
@ApiModel(value = "统一返回类型")
public class Result {
    /*响应码*/
    @ApiModelProperty(value = "响应码")
    private Integer code;

    /*提示信息*/
    @ApiModelProperty(value = "提示信息")
    private String msg;

    /*对象数据*/
    @ApiModelProperty(value = "对象数据")
    private Object object;

    /*集合数据*/
    @ApiModelProperty(value = "集合数据")
    private List list;

    /*Map集合数据*/
    @ApiModelProperty(value = "Map集合数据")
    private Map map;

    /*储存token*/
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /*使用枚举值*/
    public void setCodeEnum(Code codeEnum){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getText();
    }

    /*静态工厂方法: 成功*/
    public static Result SUCCESS(){
        Result result = new Result();
        result.setCodeEnum(Code.SUCCESS);
        return result;
    }

    /*静态工厂方法: 失败*/
    public static Result FAIL(){
        Result result = new Result();
        result.setCodeEnum(Code.FAILURE);
        return result;
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                ", list=" + list +
                ", map=" + map +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
