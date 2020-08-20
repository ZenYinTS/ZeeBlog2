package com.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    //easy ui的pagenation要求的数据格式
    private Long total;    //总记录数
    private List rows;    //总记录列表
}
