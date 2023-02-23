package com.powernode.dataservice.mapper;

import com.powernode.api.model.IncomeRecord;

import java.util.Date;
import java.util.List;

public interface IncomeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);

    /**
     * 获取到期时间为当期日期的收益计划 产品集合
     * @return
     */
    List<IncomeRecord> selectExpireIncomeRecordList();
}