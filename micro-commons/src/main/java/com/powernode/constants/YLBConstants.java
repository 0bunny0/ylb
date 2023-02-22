package com.powernode.constants;

/**
 * 常量类
 */
public class YLBConstants {
    /*产品类型*/
    public static final Integer PRODUCT_TYPE_XINSHOUBAO = 0; //新手宝
    public static final Integer PRODUCT_TYPE_YOUXUAN = 1; //优选
    public static final Integer PRODUCT_TYPE_SANBIAO = 2; //散标

    /*产品状态*/
    public static final Integer PRODUCT_STATUS_NOTFULL = 0; //未满标
    public static final Integer PRODUCT_STATUS_FULL = 1;    //已满标
    public static final Integer PRODUCT_STATUS_PLAN = 2;    //满标且已生成收益计划

    /*订单状态*/
    public static final Integer BID_STATUS_SUCCESS = 1;
    public static final Integer BID_STATUS_FAILURE = 2;

    /*收益状态*/
    public static final Integer INCOME_STATUS_PLAN = 0; //收益计划 正在收益中
    public static final Integer INCOME_STATUS_BACK = 1; //完成计划 已返还本息

}
