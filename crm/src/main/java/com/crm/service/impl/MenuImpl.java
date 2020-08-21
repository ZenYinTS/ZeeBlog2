package com.crm.service.impl;

import com.crm.domain.Menu;
import com.crm.mapper.MenuMapper;
import com.crm.service.IMenuService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuImpl implements IMenuService {

    @Autowired
    private MenuMapper menuDao;

    @Override
    public List<Menu> queryForRoot() {
        return menuDao.queryForRoot();
    }
}
