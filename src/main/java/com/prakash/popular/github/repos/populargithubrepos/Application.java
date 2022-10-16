package com.prakash.popular.github.repos.populargithubrepos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "An Application for fetching the Popular GitHub Repositories", version = "1.0.0"))
@EnableCaching
public class Application {
    // used lombok to remove boiler plate codes
    // WebClient used for synchronous REST communication
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
