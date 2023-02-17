package com.powernode.api.service;

import com.powernode.api.model.User;

public interface UserService {
    /**
     *通过手机号码获取用户对象
     * @param phone 手机号码
     * @return
     */
    User findUserByPhone(String phone);

    /**
     * 注册 用户 同时 开户
     * @param phone 手机号码
     * @param loginPassword 32位md5加密之后的密码
     * @return
     */
    User userRegister(String phone, String loginPassword);
}
