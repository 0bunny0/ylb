package com.powernode.controller;

import com.powernode.api.model.User;
import com.powernode.api.service.UserService;
import com.powernode.common.Code;
import com.powernode.resp.Result;
import com.powernode.service.SmsService;
import com.powernode.util.CommonUtil;
import com.powernode.vo.UserParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户api接口
 */
@RestController
public class UserController {

    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    private UserService userService;

    @Autowired
    private SmsService smsService;


    /*手机号码是否已注册*/
    @GetMapping("/v1/user/isRegister")
    public Result phoneExists(String phone){
        Result result = Result.FAIL();

        /*1 验证参数*/
        if(!CommonUtil.checkPhone(phone)){
            result.setCodeEnum(Code.PHONE_FORMAT_ERROR);
            return result;
        }

        /*2 访问service获取数据结果*/
        User user = userService.findUserByPhone(phone);
        if(user!=null){
            result.setCodeEnum(Code.PHONE_HAD_REGISTER);
            return result;
        }
        result = Result.SUCCESS();

        /*3 封装数据，响应结果*/

        return result;
    }


    /*注册*/
    @PostMapping("/v1/user/register")
    public Result register(@RequestBody UserParam userParam){//将json字符串 反序列化为json对象
        Result result = Result.FAIL();
        /*验证参数*/
        if(!CommonUtil.checkPhone(userParam.getPhone())
                ||userParam.getCode()==null
                ||userParam.getCode().length()!=6
                ||userParam.getLoginPassword()==null
                ||userParam.getLoginPassword().length()!=32
        ){
            System.out.println("11111");
            result.setCodeEnum(Code.QUERY_PARAM_ERROR);
            return result;
        }

        /*验证 短信验证码 是否输入正确*/
        boolean checkResult = smsService.checkSmsCode(userParam.getPhone(),userParam.getCode(),"reg");
        if(!checkResult){
            result.setCodeEnum(Code.SMS_CODE_INVALIDATE);
            return result;
        }
        /*判断用户是否注册*/
        User user = userService.findUserByPhone(userParam.getPhone());
        if(user!=null){
            result.setCodeEnum(Code.PHONE_HAD_REGISTER);
            return result;
        }

        /*调用注册服务*/
        User register = userService.userRegister(userParam.getPhone(),userParam.getLoginPassword());
        if(register==null){
            return result;
        }
        /*注册成功*/
        result = Result.SUCCESS();


        /*响应*/
        return result;

    }
}
