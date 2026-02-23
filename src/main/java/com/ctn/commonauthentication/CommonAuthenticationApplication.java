package com.ctn.commonauthentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ctn.commonauthentication.util", "com.ctn.commonauthentication.repository"}, annotationClass = org.apache.ibatis.annotations.Mapper.class)
public class CommonAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonAuthenticationApplication.class, args);
    }

}
