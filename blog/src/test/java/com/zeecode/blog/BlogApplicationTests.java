package com.zeecode.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        //获取文件的原始名称  1.jpg
        String originalFilename  = "aaa.jpg";
        //获取后缀名点的位置
        int lastIndex = originalFilename.lastIndexOf(".");
        //获取文件名 1
        String fileName = originalFilename.substring(0,lastIndex);
        //获取文件的后缀名 .jpg
        String suffix = originalFilename.substring(lastIndex);

        //拼接
        System.out.println(fileName+UUID.randomUUID()+suffix);
    }

}
