package com.powernode.dataservice.service;

import com.powernode.api.model.BidInfo;
import com.powernode.api.model.FinanceAccount;
import com.powernode.api.model.ProductInfo;
import com.powernode.api.service.InvestService;
import com.powernode.constants.YLBConstants;
import com.powernode.dataservice.mapper.BidInfoMapper;
import com.powernode.dataservice.mapper.FinanceAccountMapper;
import com.powernode.dataservice.mapper.ProductInfoMapper;
import com.powernode.util.CommonUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@DubboService(interfaceClass = InvestService.class,version = "1.0.0")
public class InvestServiceImpl implements InvestService {

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    /**
     * 用户投资了某个产品一定金额
     *
     * @param uid
     * @param productId
     * @param bidMoney
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)   //悲观锁 必须结合事务 才有效
    public boolean investProduct(Integer uid, Integer productId, BigDecimal bidMoney) {
//          测试全局异常捕捉效果
//        if (true){
//            throw new RuntimeException("手动抛异常");
//        }
        boolean flag = false;
        /*检查用户是否开户：使用悲观锁（排他锁、行级锁）*/
        FinanceAccount financeAccount = financeAccountMapper.selectByUidForUpdate(uid);
        if (financeAccount==null){
            return flag;
        }
        /*判断资金是否足够*/
        if (!CommonUtil.compareMoney(financeAccount.getAvailableMoney(),bidMoney)){
            return flag;
        }
        /*获取产品信息 使用排它锁，判断是否是未满标产品  */
        ProductInfo productInfo = productInfoMapper.selectByIdForUpdate(productId);
        if(productInfo==null||productInfo.getProductStatus()!= YLBConstants.PRODUCT_STATUS_NOTFULL){
            return flag;
        }
        /*检查投资金额 与产品 剩余投资金额、最大最小投资要求 是否满足*/
        if (!checkMoney(productInfo,bidMoney)){
            return false;
        }
        /*扣住用户账户余额*/
        int rows = financeAccountMapper.reduceAvailableMoney(uid,bidMoney);
        if (rows<1){//没有修改成功
            throw new RuntimeException("扣除账户余额失败");
        }
        /*扣除产品剩余可投资金额*/
        rows = productInfoMapper.reduceProductLeftMoney(productId,bidMoney);
        if (rows<1){//没有修改成功
            throw new RuntimeException("扣除产品剩余可投资金额失败");
        }
        /*如果扣除后 产品剩余可投资金额 为0  则需要修改产品状态为满标、满标时间*/
        productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (productInfo.getLeftProductMoney().compareTo(new BigDecimal("0"))==0){
            rows = productInfoMapper.updateFullTimeStatus(productId,new Date());
            if (rows<1){
                throw new RuntimeException("修改产品状态失败");
            }
        }
        /*添加 投资记录*/
        BidInfo bidInfo = new BidInfo();
        bidInfo.setUid(uid);
        bidInfo.setProdId(productId);
        bidInfo.setBidMoney(bidMoney);
        bidInfo.setBidTime(new Date());
        bidInfo.setBidStatus(YLBConstants.BID_STATUS_SUCCESS);
        rows = bidInfoMapper.insertSelective(bidInfo);
        if (rows<1){
            throw new RuntimeException("添加订单失败");
        }


        flag=true;
        return flag;
    }

    /**
     * 比较购买金额是否满足产品需求
     * @param productInfo   产品对象
     * @param bidMoney      购买的金额
     * @return
     */
    private boolean checkMoney(ProductInfo productInfo,BigDecimal bidMoney){
        /*比较 投资金额是否是100的倍数*/
        if (bidMoney.intValue()<100||bidMoney.intValue()%100!=0){
            return false;
        }
        /*产品的剩余投资金额 是否满足购买*/
        if (!CommonUtil.compareMoney(productInfo.getLeftProductMoney(),bidMoney)){
            return false;
        }
        /*产品的最大投资金额 是否 大于等于 投资金额*/
        if (!CommonUtil.compareMoney(productInfo.getBidMaxLimit(),bidMoney)){
            return false;
        }
        /*产品的 最小投资金额 是否 小于等于 投资金额*/
        if (!CommonUtil.compareMoney(bidMoney,productInfo.getBidMinLimit())){
            return false;
        }
        return true;
    }
}
