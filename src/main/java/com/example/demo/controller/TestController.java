package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TestController {
    @PostMapping("/test")
    public Object test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("msg", "???");
        return map;
    }
}
