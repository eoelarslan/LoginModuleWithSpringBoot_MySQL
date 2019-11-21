package com.login.module;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.io.IOException;


@SpringBootApplication
public class LoginModuleApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(LoginModuleApplication.class, args);
        System.out.println("Hello World");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
