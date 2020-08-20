package com.crm.service.impl;

import com.crm.domain.Log;
import com.crm.mapper.LogMapper;
import com.crm.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogImpl implements ILogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return logMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Log record) {
        return logMapper.insert(record);
    }

    @Override
    public Log selectByPrimaryKey(Long id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Log> selectAll() {
        return logMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Log record) {
        return logMapper.updateByPrimaryKey(record);
    }
}
