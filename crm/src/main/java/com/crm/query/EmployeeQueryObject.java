package com.crm.query;

import lombok.Data;

@Data
public class EmployeeQueryObject extends QueryObject{

    private String keyWord;    //查询时用到的关键字

}
