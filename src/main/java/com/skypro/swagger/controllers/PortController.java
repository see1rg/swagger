package com.skypro.swagger.controllers;

import org.springframework.util.StopWatch;
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
        StopWatch stopWatch = new StopWatch();
        int num = 1_000_000;
        stopWatch.start();

        int result = IntStream
                .range(1, num + 1)
                .parallel()
                .sum();

        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        return result;
    }
}
