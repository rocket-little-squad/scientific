package com.ts.scientific;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ts.scientific")
@EnableScheduling
public class ScientificApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScientificApplication.class, args);
    }

}
