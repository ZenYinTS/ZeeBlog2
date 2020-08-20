package com.crm.service;

import com.crm.domain.Permission;
import com.crm.domain.PageResult;
import com.crm.domain.Permission;
import com.crm.query.PermissionQueryObject;
import com.crm.query.QueryObject;

import java.util.List;

public interface IPermissionService {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    PageResult queryForPage(PermissionQueryObject queryObject);

    List<String> queryPermissionByEid(Long eid);
}
