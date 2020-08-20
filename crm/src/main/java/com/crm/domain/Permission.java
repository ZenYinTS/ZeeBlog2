package com.crm.domain;

import lombok.Data;

@Data
public class Permission {
    private Long id;

    private String sn;

    private String resource;

    //由于这里不要求从权限获取到所有角色，所以可以不用添加角色属性
}