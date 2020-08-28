package com.zeecode.blog.service;

import com.zeecode.blog.pojo.User;

public interface UserService {
    User checkUser(String username,String password);
}
