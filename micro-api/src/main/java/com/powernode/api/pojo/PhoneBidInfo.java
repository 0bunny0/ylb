package com.powernode.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 投资记录信息和对应的用户手机号码
 */
public class PhoneBidInfo implements Serializable {

    private Integer id;
    private String phone;   //需要脱敏
    private BigDecimal bidMoney;
    private Date bidTime;   //数据库中的时间

    private String showTime;    //显示在页面中的时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*脱敏*/
    public String getPhone() {
        if(this.phone==null||phone.length()!=11){
            phone="***********";
        }
        phone = phone.substring(0,3)+"****"+phone.substring(phone.length()-4);
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public String getShowTime() {
        if(this.bidTime==null){
            this.showTime="----------";
            return this.showTime;
        }
        /*日期格式转换*/
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.showTime = format.format(this.bidTime);
        return this.showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;

    }
}
