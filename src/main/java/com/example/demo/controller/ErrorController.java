package com.example.demo.controller;

import com.example.demo.model.service.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
@Slf4j
public class ErrorController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping("/404")
    public Result ErrorNotFound() {
        return Result.failure("请求的资源不存在", HttpStatus.NOT_FOUND.value());
    }

    // TODO 待处理
    @RequestMapping("/500")
    public Result ServerError() {
        log.error("服务器发生内部错误");
        return Result.failure("服务器发生错误", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
