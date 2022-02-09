package io.swagger.client.controller;

import io.swagger.client.service.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    public void runApplication() {
        apiService.runApplication();
        System.exit(1);
    }
}
