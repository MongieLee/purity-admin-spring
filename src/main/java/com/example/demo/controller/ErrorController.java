package com.example.demo.controller;

import com.example.demo.model.service.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
@Slf4j
public class ErrorController {
    @RequestMapping("/404")
    public Result ErrorNotFound(HttpServletRequest request) {
        return Result.failure("资源不存在", 404);
    }

    @RequestMapping("/500")
    public Result ServerError() {
        log.error("服务器发生内部错误");
        return Result.failure("服务器发生错误", 500);
    }
}
