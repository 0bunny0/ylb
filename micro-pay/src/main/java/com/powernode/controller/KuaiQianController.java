package com.powernode.controller;

import com.powernode.api.model.User;
import com.powernode.api.service.UserService;
import com.powernode.service.KuaiQianService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Map;

/**
 * 快钱api接口
 * 生成支付表单页面的 接口
 * 快钱访问的 异步通知接口
 */
@Controller
public class KuaiQianController {

    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    private UserService userService;

    @Autowired
    private KuaiQianService kuaiQianService;

    /*生成支付表单页面的接口*/
    @GetMapping("/kq/receive/pay")
    public String receivePay(Integer uid, BigDecimal money, Model model){
        /*验证参数*/
        if (uid==null||uid<1||money.doubleValue()<=0){
            return "error";
        }
        /*获取用户信息 判断 用户是否存在*/
        User user = userService.findUserById(uid);
        if (user==null){
            return "error";
        }
        /*封装表单需要的 数据*/
//        String merchantAcctId = "1001214035601";//
//        model.addAttribute("inputCharset",merchantAcctId);
        Map<String,String> data =kuaiQianService.generatoryKqFormData(uid,user.getPhone(),money);
        model.addAllAttributes(data);//参数为 map 集合
        /*添加充值记录 状态为 充值中*/
        kuaiQianService.addRecharge(uid,money,data);

        return "kqForm";
    }


    /*快钱访问的 异步通知接口; 接收快钱发送的 支付结果*/
    @GetMapping("/kq/notify")
    @ResponseBody
    public String kqNotify(HttpServletRequest request){//request 中 存在 快钱发送的 若干个 数据
        System.out.println("快钱执行了异步通知");

        /*测试request中是否有参数*/
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuffer buffer = new StringBuffer();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            buffer.append(name).append("=").append(value).append("&");
        }

        System.out.println(buffer);     //测试数据
        /*处理支付结果*/
        kuaiQianService.handleKuaiQianNotify(request);

        return "<result>1</result><redirecturl>http://www.yoursite.com/show.asp</redirecturl>";
    }
}
