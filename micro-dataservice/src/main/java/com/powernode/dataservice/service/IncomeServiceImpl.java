package com.powernode.dataservice.service;

import com.powernode.api.model.BidInfo;
import com.powernode.api.model.IncomeRecord;
import com.powernode.api.model.ProductInfo;
import com.powernode.api.service.IncomeService;
import com.powernode.constants.YLBConstants;
import com.powernode.dataservice.mapper.BidInfoMapper;
import com.powernode.dataservice.mapper.IncomeRecordMapper;
import com.powernode.dataservice.mapper.ProductInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = IncomeService.class, version = "1.0.0")
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    /**
     * 生成收益计划:
     * 获取昨天满标的产品集合
     * 获取产品的投资记录集合
     * 遍历集合 生成收益计划
     * 修改产品状态为 2
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generatorIncomePlan() {
        Date now =new Date();
        /*昨天是一个时间段：昨天的0点 到今天的0点*/
        Date startDate = DateUtils.truncate(DateUtils.addDays(now,-1), Calendar.DATE);//保留天，时分秒为0
        Date endDate = DateUtils.truncate(now, Calendar.DATE);
        /*1 获取昨天满标的 产品集合*/
        List<ProductInfo> productInfoList =
                productInfoMapper.selectFullProduct(startDate,endDate);
        /*2 遍历产品集合 获取每个产品的 投资记录集合*/
        productInfoList.forEach(productInfo -> {
            List<BidInfo> bidInfoList =
                    bidInfoMapper.selectByProductId(productInfo.getId());
            /*日利率*/
            BigDecimal dayRate = productInfo.getRate()
                    .divide(new BigDecimal("360"),10, RoundingMode.HALF_UP)
                    .divide(new BigDecimal("100"));//精确到小数点后10位
            /*3 遍历投资记录集合 生成收益计划    每一个投资记录对应一个 收益计划  计算利息，到期时间 */
            bidInfoList.forEach(bidInfo -> {
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecord.setProdId(bidInfo.getProdId());
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setIncomeStatus(YLBConstants.INCOME_STATUS_PLAN);
                incomeRecord.setBidId(bidInfo.getId());
                BigDecimal incomeMoney =null;
                Date incomeDate = null;
                /*根据不同产品类型 计算不同的  利息 和 收益时间*/
                if (productInfo.getProductType()==YLBConstants.PRODUCT_TYPE_XINSHOUBAO){
                    /*天为到位*/
                    /*利息  金*日利率*天数 */
                    incomeMoney=bidInfo.getBidMoney().multiply(dayRate).multiply(new BigDecimal(productInfo.getCycle()));
                    /*到期时间*/
                    incomeDate=DateUtils
                            .addDays(productInfo.getProductFullTime(),productInfo.getCycle()+1);//满标的第二天算收益，+1
                }else {
                    /*月为单位*/
                    incomeMoney=bidInfo.getBidMoney().multiply(dayRate).multiply(new BigDecimal(productInfo.getCycle()*30));
                    /*到期时间*/
                    incomeDate=DateUtils
                            .addDays(productInfo.getProductFullTime(),productInfo.getCycle()*30+1);//满标的第二天算收益，+1
                }
                incomeRecord.setIncomeMoney(incomeMoney);//利息   本金*日利率*天数
                incomeRecord.setIncomeDate(incomeDate);//收益时间 当前时间+周期天数
                int rows = incomeRecordMapper.insertSelective(incomeRecord);
                if (rows<1){
                    throw new RuntimeException("添加收益计划失败");
                }
            });
            /*4 修改产品的状态*/
            int rows
                    = productInfoMapper.updateStatus(productInfo.getId(), YLBConstants.PRODUCT_STATUS_PLAN);
            if (rows<1){
                throw new RuntimeException("修改产品划失败");
            }
        });
    }
}
