package com.example.demo;

import lyj.forward.generation.annotation.EnableAutoForwardGeneration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoForwardGeneration(entityPackages="com.example.demo.entity",OnOff = true)
public class DemoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }
}
