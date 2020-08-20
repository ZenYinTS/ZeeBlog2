package com.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;

    private String username;

    private String realname;

    private String password;

    private String tel;

    private String email;

    private Department dept;

    @JsonFormat(pattern = "yyyy-MM-dd",locale = "GTM+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date inputTime;

    private Boolean state;

    private Boolean admin;

    //需要获取每个用户对应的角色
    private List<Role> roles;
}