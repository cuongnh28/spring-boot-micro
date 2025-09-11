package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TestController {
    @GetMapping("/")
    public String demo() {
        return "hello";
    }

    @GetMapping("/x")
    public String demoX() {
        throw new RuntimeException("Runtime exception");
    }

}
