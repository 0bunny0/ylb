package com.powernode.dataservice.mapper;

import com.powernode.api.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取总用户数
     * @return
     */
    Integer selectCountUsers();

    /**
     * 通过手机号码获取用户信息
     * @param phone 手机号码
     * @return
     */
    User selectByPhone(String phone);

    /**
     * 添加用户信息 返回新增信息的id 赋值给 user.id
     * @param user
     */
    int insertUserReturnId(User user);

    User selectLogin(String phone, String loginPassword);
}