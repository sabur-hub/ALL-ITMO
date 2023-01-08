package com.example.blps_lab4;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class BlpsLab4Application {

    public static void main(String[] args) {
        SpringApplication.run(BlpsLab4Application.class, args);
    }

}
