package com.skypro.swagger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/port")
public class PortController {
    @GetMapping
    public int getPort(HttpServletRequest request) {
        return request.getServerPort();
    }

    @GetMapping("/integer-value")
    public float integerValue() {
        long start = System.currentTimeMillis();
        int num = 1_000_000;

        int result = IntStream
                .range(1, num + 1)
                .parallel()
                .sum();

        System.out.println(System.currentTimeMillis() - start);
        return result;
    }
}
