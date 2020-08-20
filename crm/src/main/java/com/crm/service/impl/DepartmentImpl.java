package com.crm.service.impl;

import com.crm.domain.Department;
import com.crm.mapper.DepartmentMapper;
import com.crm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return departmentDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Department record) {
        return departmentDao.insert(record);
    }

    @Override
    public Department selectByPrimaryKey(Long id) {
        return departmentDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> selectAll() {
        return departmentDao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return departmentDao.updateByPrimaryKey(record);
    }

    @Override
    public List<Department> queryForEmp() {
        return departmentDao.queryForEmp();
    }
}
