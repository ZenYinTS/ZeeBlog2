package com.crm.query;

import lombok.Data;

@Data
public class QueryObject {
    //点击下一页时发送的请求包含以下两个字段
    private Integer page;    //当前页
    private Integer rows;    //页面记录数
    //获取开始位置，分页查询的limit会从start的下一个记录开始，累加rows条记录
    public Integer getStart(){
        return page==null?0:(page-1)*rows;
    }
}
