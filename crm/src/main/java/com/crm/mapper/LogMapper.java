package com.crm.mapper;

import com.crm.domain.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Log record);

    Log selectByPrimaryKey(Long id);

    List<Log> selectAll();

    int updateByPrimaryKey(Log record);
}