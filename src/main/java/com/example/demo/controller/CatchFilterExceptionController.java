package com.example.demo.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CatchFilterExceptionController {
    @RequestMapping("/error/throwEx")
    public void throwException(HttpServletRequest request) {
        throw (JWTVerificationException) request.getAttribute("filter.error");
    }
}
