package io.swagger.client;

import io.swagger.client.controller.ApiController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;


@SpringBootApplication
public class ApiApp {

    private static Properties properties = new Properties();

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiApp.class, args);

        context.getBean(ApiController.class).runApplication();
    }
}
