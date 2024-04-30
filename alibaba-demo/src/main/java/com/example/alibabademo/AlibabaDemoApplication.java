package com.example.alibabademo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.alibabademo.mapper")
public class AlibabaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaDemoApplication.class, args);
    }

}
