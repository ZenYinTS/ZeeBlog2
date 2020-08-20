package com.crm.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Log {
    private Long id;

    private Employee opuser;

    private Date optime;

    private String opip;

    private String function;

    private String params;
}