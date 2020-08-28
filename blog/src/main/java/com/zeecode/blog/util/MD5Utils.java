package com.zeecode.blog.util;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    public static void main(String[] args) {
        String s = DigestUtils.md5DigestAsHex("111111".getBytes());
        System.out.println(s);
    }

}
