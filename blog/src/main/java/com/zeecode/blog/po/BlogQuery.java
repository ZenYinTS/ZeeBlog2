package com.zeecode.blog.po;

import lombok.Data;

@Data
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
}
