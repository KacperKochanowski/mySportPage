package com.mySportPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MySportPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySportPageApplication.class, args);
    }
}
