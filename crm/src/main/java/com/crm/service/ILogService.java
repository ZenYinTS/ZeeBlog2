package com.crm.service;

import com.crm.domain.Log;

import java.util.List;

public interface ILogService {
    int deleteByPrimaryKey(Long id);

    int insert(Log record);

    Log selectByPrimaryKey(Long id);

    List<Log> selectAll();

    int updateByPrimaryKey(Log record);

}
