package com.powernode.controller;

import com.powernode.api.model.FinanceAccount;
import com.powernode.api.model.User;
import com.powernode.api.service.FinanceAccountService;
import com.powernode.api.service.UserService;
import com.powernode.common.Code;
import com.powernode.constants.RedisKey;
import com.powernode.resp.Result;
import com.powernode.service.SmsService;
import com.powernode.util.CommonUtil;
import com.powernode.util.TokenUtil;
import com.powernode.vo.UserParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户api接口
 */
@RestController
public class UserController {

    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    private UserService userService;


    @Autowired
    private SmsService smsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    private FinanceAccountService financeAccountService;


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


    /*登录 单点登录*/
    @PostMapping("/v1/token/accessToken")
    public Result accessToken(@RequestBody UserParam userParam){
        Result result = Result.FAIL();
        /*验证参数*/
        if(!CommonUtil.checkPhone(userParam.getPhone())
                ||userParam.getCode()==null
                ||userParam.getCode().length()!=6
                ||userParam.getLoginPassword()==null
                ||userParam.getLoginPassword().length()!=32
        ){
            result.setCodeEnum(Code.QUERY_PARAM_ERROR);
            return result;
        }

        /*验证验证码是否正确*/
        boolean checkResult = smsService.checkSmsCode(userParam.getPhone(), userParam.getCode(), "login");
        if(!checkResult){
            result.setCodeEnum(Code.SMS_CODE_INVALIDATE);
            return result;
        }

        /*3 登录验证*/
        User user = userService.userLogin(userParam.getPhone(),userParam.getLoginPassword());
        if(user==null){
            result.setCodeEnum(Code.SMS_LOGIN_ERROR);
            return result;
        }
        /*基于session认证: 往session区间存入user对象
        基于token认证: 认证成功后 响应给客户端一个jwt
        * */

        /*4 生成token*/
        Map<String,Object> payLoad = new HashMap<>();
        payLoad.put("uid",user.getId());
        payLoad.put("name",user.getName());
        String jwt = tokenUtil.createJwt(payLoad,120);


        /*5 封装数据响应结果*/
        //先创建对象 再去赋值
        result = Result.SUCCESS();
        result.setAccessToken(jwt);

        /*补充前端 需要储存的数据*/
        Map<String,Object> userView = new HashMap<>();
        userView.put("uid",user.getId());
        userView.put("name",user.getName());
        userView.put("phone",user.getPhone());
        result.setObject(userView);

        /*将token 存入redis 用户 登出（实现从无状态变成有状态）*/
        BoundValueOperations<String,String> boundValueOps = stringRedisTemplate.boundValueOps(RedisKey.TOKEN_KEY+jwt);
        boundValueOps.set(jwt,2, TimeUnit.HOURS);

        return result;
    }

    /*登出: 将redis中的token删除*/
    @PostMapping("/v1/user/logout")
    public Result logout(HttpServletRequest request){
        Result result = Result.SUCCESS();

        /*从请求头中获取token*/
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);

        /*删除token*/
        stringRedisTemplate.delete(RedisKey.TOKEN_KEY+token);

        return result;
    }


    @PostMapping("/v1/user/info")
    public Result userInfo(HttpServletRequest request){
        Result result = Result.SUCCESS();

        /*业务逻辑*/
        /*从request区间获取 拦截器传递的参数 tokenUid*/
        Integer uid = Integer.parseInt(request.getAttribute("uid").toString());

        /*通过token中的 uid 访问数据服务*/
        User user = userService.findUserById(uid);

        //FinanceAccount financeAccount = financeAccountService.queryByUid(uid);

//        UserInfo userInfo = new UserInfo();
//        userInfo.setName(user.getName());
//        userInfo.setAccountMoney(financeAccount.getAvailableMoney());
//
//        result = Result.SUCCESS();
//        result.setObject(userInfo);

        return result;
    }


}
