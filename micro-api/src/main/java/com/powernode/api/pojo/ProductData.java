package com.powernode.api.pojo;

import com.powernode.api.model.ProductInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 首页三个集合
 */
public class ProductData implements Serializable {
    private List<ProductInfo> xinShouBao;
    private List<ProductInfo> youXuan;
    private List<ProductInfo> sanBiao;

    @Override
    public String toString() {
        return "ProductData{" +
                "xinShouBao=" + xinShouBao +
                ", youXuan=" + youXuan +
                ", sanBiao=" + sanBiao +
                '}';
    }

    public List<ProductInfo> getXinShouBao() {
        return xinShouBao;
    }

    public void setXinShouBao(List<ProductInfo> xinShouBao) {
        this.xinShouBao = xinShouBao;
    }

    public List<ProductInfo> getYouXuan() {
        return youXuan;
    }

    public void setYouXuan(List<ProductInfo> youXuan) {
        this.youXuan = youXuan;
    }

    public List<ProductInfo> getSanBiao() {
        return sanBiao;
    }

    public void setSanBiao(List<ProductInfo> sanBiao) {
        this.sanBiao = sanBiao;
    }
}
