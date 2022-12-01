package com.example.demo.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/10/25 14:56
 */
@RestController
public class AController {
    @GetMapping("/aaa")
    public void aaa(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        int b;
        while ((b = inputStream.read()) != -1) {
            System.out.print((char) b);
        }
        System.out.println(request.getMethod());
    }
}
