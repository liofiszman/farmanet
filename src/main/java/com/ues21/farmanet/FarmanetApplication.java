package com.ues21.farmanet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;


@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class FarmanetApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmanetApplication.class, args);

    }


}
