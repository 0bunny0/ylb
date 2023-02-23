package com.powernode.task;

import com.powernode.api.service.IncomeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;


/*
    定时任务管理类
 */
@Component
public class TaskManager {

    @DubboReference(interfaceClass = IncomeService.class,version = "1.0.0")
    private IncomeService incomeService;

    /*时间表达式 秒 分 时 天 月 周 (年)*/
//    @Scheduled(cron = "* * * ? * 2-6")
//    public void testTask(){
//        System.out.println("定时任务执行中: "+new Date());
//    }

    /*生成收益计划:
    每天凌晨2点触发一次: 获取前一天满标产品的投资记录,通过投资记录生成收益(计划,状态为 0)
    * */
    @Scheduled(cron = "0 0 2 * * ?")
    public void generatorIncomePlan(){

        /*远程服务调用*/
        incomeService.generatorIncomePlan();
    }

    /*完成收益计划:
    每天上午8点触发一次:
    获取到期时间为当前日期的 收益计划(状态为 0 的收益信息) 获取收益集合
    遍历收益集合
    获取每一个集合的 本金 和 利息 = 本息
    通过收益表的 uid  去修改 某用户的 账户 余额  余额 = 余额 + 本息
    修改收益的 状态 为 1
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void completeIncomePlan(){
        incomeService.completeIncomePlan();
    }

}
