package com.javalord.product_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/gateway")
public class GatewayController {

    @GetMapping(value = "/test1")
    public String test1() {
        log.info("test1");

        return "test1";
    }

    @GetMapping(value = "test2")
    public String test2() {
        log.info("test2");

        return "test2";
    }

}
