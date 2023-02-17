package com.powernode.vo;

/**
 * 前端发送的表单、json的数据模型
 */
public class UserParam {

    private String phone;
    private String loginPassword;
    private String code;

    @Override
    public String toString() {
        return "UserParam{" +
                "phone='" + phone + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
