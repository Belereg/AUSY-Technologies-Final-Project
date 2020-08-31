package com.ausy_technologies.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;

@SpringBootApplication

public class FinalprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalprojectApplication.class, args);
    }

}
