package com.crm.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role {
    private Long id;

    private String sn;

    private String name;

    //需要根据角色获取到对应的权限信息，所以在这里添加权限列表的属性
    private List<Permission> permissions = new ArrayList<Permission>();
}