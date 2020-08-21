package com.crm.mapper;

import com.crm.domain.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    List<Menu> queryForRoot();
}