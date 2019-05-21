package com.example.sports;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.example.sports.mapper","com.example.sports.dao"})
//@EnableAutoConfiguration
@SpringBootApplication
public class SportsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportsApplication.class, args);
    }






}
