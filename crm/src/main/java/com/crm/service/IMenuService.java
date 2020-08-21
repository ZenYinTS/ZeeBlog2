package com.crm.service;

import com.crm.domain.Menu;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
public interface IMenuService {
    List<Menu> queryForRoot();
}
