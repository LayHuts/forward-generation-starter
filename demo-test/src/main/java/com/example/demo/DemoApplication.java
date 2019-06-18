package com.example.demo;

import lyj.forward.generation.annotation.EnableAutoForwardGeneration;
import lyj.forward.generation.annotation.LNotTableField;
import lyj.forward.generation.enums.DdlType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoForwardGeneration(entityPackages="com.example.demo.entity",OnOff = true)
public class DemoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }
}
