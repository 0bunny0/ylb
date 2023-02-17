package com.powernode.controller;

import com.powernode.common.Code;
import com.powernode.resp.Result;
import com.powernode.service.SmsService;
import com.powernode.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取短信验证码api接口
 */
@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     *
     * @param phone 手机号码
     * @param cmd   操作: reg login realname
     * @return
     */
    /*获取短信验证码*/
    @GetMapping("/v1/sms/code")
    public Result sendSmsCode(String phone,String cmd){

        Result result = Result.FAIL();

        /*1 验证参数*/
        if(!CommonUtil.checkPhone(phone)){
            result.setCodeEnum(Code.PHONE_FORMAT_ERROR);
            return result;
        }

        /*2 验证操作*/
        if(!"req".equals(cmd)&&"login".equals(cmd)&&"realname".equals(cmd)){
            result.setCodeEnum(Code.QUERY_PARAM_ERROR);
            return result;
        }

        /*3 判断redis中是否 存在验证码 防止恶意访问*/
        boolean hadCode = smsService.checkCodeIsUse(phone,cmd);
        if(hadCode){
            /*验证码可用，不需要再次发送*/
            result.setCodeEnum(Code.SMS_CODE_EXISTS);
            return result;
        }
        /*4 发送短信*/
        boolean sendResult = smsService.sendSmsCode(phone,cmd);
        if(!sendResult){
            result.setCodeEnum(Code.SMS_SEND_ERROR);
            return result;
        }

        result = Result.SUCCESS();
        return result;
    }
}
