package com.example.demo.controller;

import com.example.demo.model.service.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/404")
    public Result ErrorNotFound() {
        System.out.println("456");
        return Result.failure("资源不存在", 404);
    }

    @RequestMapping("/500")
    public Result ServerError() {
        System.out.println("123");
        return Result.failure("服务器发生错误", 500);
    }
}
