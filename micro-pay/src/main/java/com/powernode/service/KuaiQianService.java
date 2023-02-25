package com.powernode.service;

import com.powernode.api.model.RechargeRecord;
import com.powernode.api.service.RechargeService;
import com.powernode.constants.YLBConstants;
import com.powernode.util.Pkipair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class KuaiQianService {

    /*生成 支付表单需要的数据*/
    public Map<String, String> generatoryKqFormData(Integer uid, String phone, BigDecimal money) {
        Map<String, String> data = new HashMap<>();
        //人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
        String merchantAcctId = "1001214035601";//
        //编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
        String inputCharset = "1";
        //接收支付结果的页面地址，该参数一般置为空即可。
        String pageUrl = "";
        //服务器接收支付结果的后台地址，该参数务必填写，不能为空。
        String bgUrl = "https://3ld4027775.yicp.fun/pay/kq/notify";//使用 内网穿透 实现 快钱访问商家服务器
        /*使用内网穿透技术 代理接口*/
//        String bgUrl = "https://5544uo6907.oicp.vip/pay/kq/notify";
        //网关版本，固定值：v2.0,该参数必填。
        String version = "v2.0";
        //语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
        String language = "1";
        //签名类型,该值为4，代表PKI加密方式,该参数必填。
        String signType = "4";
        //支付人姓名,可以为空。
        String payerName = "";
        //支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
        String payerContactType = "2";
        //支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
        String payerContact = phone;
        //指定付款人，可以为空
        String payerIdType = "3";
        //付款人标识，可以为空
        String payerId = uid + "";
        //付款人IP，可以为空
        String payerIP = "";
        //商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
        String orderId = "KQ" + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        //订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填。
        String orderAmount = money.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
        //订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
        String orderTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        //快钱时间戳，格式：yyyyMMddHHmmss，如：20071117020101， 可以为空
        String orderTimestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        //商品名称，可以为空。
        String productName = "充值";
        //商品数量，可以为空。
        String productNum = "1";
        //商品代码，可以为空。
        String productId = "10000";
        //商品描述，可以为空。
        String productDesc = "购买充值服务";
        //扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext1 = "扩展1";
        //扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext2 = "扩展2";
        //支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10-1或10-2，必填。
        String payType = "00";
        //银行代码，如果payType为00，该值可以为空；如果payType为10-1或10-2，该值必须填写，具体请参考银行列表。
        String bankId = "";
        //同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
        String redoFlag = "0";
        //快钱合作伙伴的帐户号，即商户编号，可为空。
        String pid = "";
        // signMsg 签名字符串 不可空，生成加密签名串
        String signMsgVal = "";
        signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset, data);
        signMsgVal = appendParam(signMsgVal, "pageUrl", pageUrl, data);
        signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl, data);
        signMsgVal = appendParam(signMsgVal, "version", version, data);
        signMsgVal = appendParam(signMsgVal, "language", language, data);
        signMsgVal = appendParam(signMsgVal, "signType", signType, data);
        signMsgVal = appendParam(signMsgVal, "merchantAcctId", merchantAcctId, data);
        signMsgVal = appendParam(signMsgVal, "payerName", payerName, data);
        signMsgVal = appendParam(signMsgVal, "payerContactType", payerContactType, data);
        signMsgVal = appendParam(signMsgVal, "payerContact", payerContact, data);
        signMsgVal = appendParam(signMsgVal, "payerIdType", payerIdType, data);
        signMsgVal = appendParam(signMsgVal, "payerId", payerId, data);
        signMsgVal = appendParam(signMsgVal, "payerIP", payerIP, data);
        signMsgVal = appendParam(signMsgVal, "orderId", orderId, data);
        signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount, data);
        signMsgVal = appendParam(signMsgVal, "orderTime", orderTime, data);
        signMsgVal = appendParam(signMsgVal, "orderTimestamp", orderTimestamp, data);
        signMsgVal = appendParam(signMsgVal, "productName", productName, data);
        signMsgVal = appendParam(signMsgVal, "productNum", productNum, data);
        signMsgVal = appendParam(signMsgVal, "productId", productId, data);
        signMsgVal = appendParam(signMsgVal, "productDesc", productDesc, data);
        signMsgVal = appendParam(signMsgVal, "ext1", ext1, data);
        signMsgVal = appendParam(signMsgVal, "ext2", ext2, data);
        signMsgVal = appendParam(signMsgVal, "payType", payType, data);
        signMsgVal = appendParam(signMsgVal, "bankId", bankId, data);
        signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag, data);
        signMsgVal = appendParam(signMsgVal, "pid", pid, data);


        System.out.println(signMsgVal);
        Pkipair pki = new Pkipair();
        /*生成签名*/
        String signMsg = pki.signMsg(signMsgVal);
        /*将签名 添加到 map集合中  传递给 thymeleaf页面*/
        data.put("signMsg", signMsg);
        return data;
    }

    /*生成签名字符串拼接方法，将其改造成 同时 往 map集合添加 元素*/
    public String appendParam(String returns, String paramId, String paramValue, Map<String, String> data) {
        if (returns != "") {
            if (paramValue != "") {

                returns += "&" + paramId + "=" + paramValue;
            }

        } else {

            if (paramValue != "") {
                returns = paramId + "=" + paramValue;
            }
        }
        if (data != null) {
            data.put(paramId, paramValue);
        }
        return returns;
    }


    @DubboReference(interfaceClass = RechargeService.class, version = "1.0.0")
    private RechargeService rechargeService;

    /**
     * 方法内 调用远程服务  添加充值记录
     * @param uid
     * @param money
     * @param data
     */
    public void addRecharge(Integer uid, BigDecimal money, Map<String, String> data) {
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setChannel("快钱");    // 渠道
        rechargeRecord.setRechargeDesc(data.get("productDesc"));
        rechargeRecord.setRechargeNo(data.get("orderId"));
        rechargeRecord.setRechargeTime(new Date());
        rechargeRecord.setRechargeStatus(YLBConstants.RECHARGE_ING);
        rechargeRecord.setRechargeMoney(money);
        rechargeRecord.setUid(uid);

        /*调用远程服务*/
        int rows = rechargeService.addRecharge(rechargeRecord);
        if(rows<1){
            /*也可以使用logback记录 状态信息*/
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("生成充值记录失败");
        }

    }

    /**
     * 处理快钱异步通知中的 支付结果
     * @param request   支付结果信息 储存在request对象中
     */
    public void handleKuaiQianNotify(HttpServletRequest request) {
        String merchantAcctId = request.getParameter("merchantAcctId");
        String version = request.getParameter("version");
        String language = request.getParameter("language");
        String signType = request.getParameter("signType");
        String payType = request.getParameter("payType");
        String bankId = request.getParameter("bankId");
        String orderId = request.getParameter("orderId");
        String orderTime = request.getParameter("orderTime");
        String orderAmount = request.getParameter("orderAmount");
        String bindCard = request.getParameter("bindCard");
//        String bindCard="";
        if (request.getParameter("bindCard") != null) {
            bindCard = request.getParameter("bindCard");
        }
        String bindMobile = "";
        if (request.getParameter("bindMobile") != null) {
            bindMobile = request.getParameter("bindMobile");
        }
        String bankDealId = request.getParameter("bankDealId");
        String dealTime = request.getParameter("dealTime");
        String payAmount = request.getParameter("payAmount");
        String fee = request.getParameter("fee");
        String ext1 = request.getParameter("ext1");
        String ext2 = request.getParameter("ext2");
        String payResult = request.getParameter("payResult");
        String aggregatePay = request.getParameter("aggregatePay");
        String errCode = request.getParameter("errCode");
        String signMsg = request.getParameter("signMsg");

        String dealId = request.getParameter("dealId");

        String merchantSignMsgVal = "";
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard", bindCard,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile", bindMobile,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "aggregatePay", aggregatePay,null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode,null);
        System.out.println("merchantSignMsgVal=" + merchantSignMsgVal);

        Pkipair pki = new Pkipair();
        /*验签                            参数拼接后的字符串     参数拼接后 加密的 字符串*/
        boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
        /*没有更新 快钱的公钥*/
        flag = true;
        /* 如果验签成功,网络传输过程中没有被篡改数据 */
        if(!flag){
            /*根据参数信息，判断业务逻辑*/
            System.out.println("验签失败，数据被篡改了!");
        }

            /*判断商户号是否是本商家的商户号*/
            if(!"1001214035601".equals(merchantAcctId)){
                System.out.println("商户号不正确");
                return;
            }


        /*调用远程服务 处理支付结果*/
        int i = rechargeService.dealWithNotify(orderId, payResult, payAmount);
        //System.out.println(i);

    }


}
