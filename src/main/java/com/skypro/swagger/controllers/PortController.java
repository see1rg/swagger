package com.skypro.swagger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PortController {
    @GetMapping("/port")
    public int getPort(HttpServletRequest request){
        return request.getServerPort();
    }
}
