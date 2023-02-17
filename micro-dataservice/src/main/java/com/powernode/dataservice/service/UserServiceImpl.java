package com.powernode.dataservice.service;

import com.powernode.api.model.FinanceAccount;
import com.powernode.api.model.User;
import com.powernode.api.service.UserService;
import com.powernode.dataservice.mapper.FinanceAccountMapper;
import com.powernode.dataservice.mapper.UserMapper;
import com.powernode.util.CommonUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;

@DubboService(interfaceClass = UserService.class,version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${password.salt}")
    private String passwordSalt;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    /**
     * 通过手机号码获取用户对象
     * @param phone 手机号码
     * @return
     */
    @Override
    public User findUserByPhone(String phone) {
        User user = null;
        /*1 校验数据*/
        if(!CommonUtil.checkPhone(phone)){
            return user;
        }

        /*2 访问dao获取数据 执行业务逻辑*/
        user = userMapper.selectByPhone(phone);

        /*3 封装数据 响应结果*/
        return user;
    }

    /**
     * 注册 用户 同时 开户
     * 加盐 二次加密
     * @param phone         手机号码
     * @param loginPassword 32位md5加密之后的密码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)  //写多行数据时。有异常则回滚
    public User userRegister(String phone, String loginPassword) {
        User user = null;
        if(!CommonUtil.checkPhone(phone)
            ||loginPassword==null
            ||loginPassword.length()!=32
        ){
            return user;
        }

        /*加盐*/
        loginPassword = loginPassword+passwordSalt;
        /*md5 加密*/
        loginPassword = DigestUtils.md5Hex(loginPassword);

        user = new User();
        user.setPhone(phone);
        user.setLoginPassword(loginPassword);
        user.setAddTime(new Date());
        userMapper.insertUserReturnId(user);

        /*开户 要使用 新增的user的 id*/
        FinanceAccount financeAccount = new FinanceAccount();
        financeAccount.setUid(user.getId());
        financeAccount.setAvailableMoney(new BigDecimal("0"));
        financeAccountMapper.insertSelective(financeAccount);

        return user;
    }
}
