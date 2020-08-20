package com.crm.util;

import lombok.Data;

@Data
public class AjaxResult {
    private boolean success = false;
    private String msg;

    //成功
    public AjaxResult(boolean success,String msg){
        this.success = success;
        this.msg = msg;
    }

    //失败
    public AjaxResult(String msg){
        this.msg = msg;
    }
}
