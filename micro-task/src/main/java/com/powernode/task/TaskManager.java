package com.powernode.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;


/*
    定时任务管理类
 */
@Component
public class TaskManager {

    /*时间表达式 秒 分 时 天 月 周 (年)*/
    @Scheduled(cron = "* * * ? * 2-6")
    public void testTask(){
        System.out.println("定时任务执行中: "+new Date());
    }

    /*生成收益计划:
    每天凌晨2点触发一次: 获取前一天满标产品的投资记录,通过投资记录生成收益(计划,状态为 0)
    * */

    /*完成收益计划:
    每天上午8点触发一次: 获取到期时间为当前日期的 收益计划(0),返还对应本息，并修改状态为 1
     */

}
