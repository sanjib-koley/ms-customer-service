package com.sanjib.edureka.ms_customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CartServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CartServiceApplication.class, args);
    }

}
