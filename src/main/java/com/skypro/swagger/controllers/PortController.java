package com.skypro.swagger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/port")
public class PortController {
    @GetMapping
    public int getPort(HttpServletRequest request){
        return request.getServerPort();
    }

    @GetMapping("/integer-value")
    public float integerValue() {
        long start = System.currentTimeMillis();
        int a = 1;
        int d = 1;
        int n = 1_000_000;
        long sum = (n / 2) * (2 * a + (n - 1) * d);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return sum;
    }
}
