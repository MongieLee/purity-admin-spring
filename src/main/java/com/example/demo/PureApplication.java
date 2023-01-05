package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableTransactionManagement
//@MapperScan( basePackages = {"com.example.demo.mapper","com.example.demo.dao"})
@MapperScan(basePackages = {"com.example.demo.dao"})
@EnableSwagger2
public class PureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PureApplication.class, args);
    }

}
