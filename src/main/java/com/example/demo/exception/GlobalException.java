package com.example.demo.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.model.service.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 处理参数校验异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result paramExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!allErrors.isEmpty()) {
                // 可能有多个校验异常，取第一个异常信息即可
                return Result.failure(allErrors.get(0).getDefaultMessage());
            }
        }
        return Result.failure("请求失败");
    }

    /**
     * 处理Post请求的参数解析异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result missingRequestBodyExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return Result.failure("参数解析失败，请检查接口传参是否正确", e.getMessage());
    }

    /**
     * 处理JWT校验身份异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JWTVerificationException.class)
    public Result TokenExceptionHandler(JWTVerificationException e) {
        log.error(e.getMessage());
        return Result.failure("token校验失败，请检查是否正确或是否过期", HttpStatus.UNAUTHORIZED.value());
    }
//    @ExceptionHandler(TokenExpiredException.class)
//    public Result TokenExceptionHandler(TokenExpiredException e) {
//        log.error(e.getMessage());
//        return Result.failure("token已过期", e.getMessage());
//    }
//    @ExceptionHandler(JWTDecodeException.class)
//    public Result TokenExceptionHandler(JWTDecodeException e) {
//        log.error(e.getMessage());
//        return Result.failure("解析token时发生错误，请校验token格式正确性", e.getMessage());
//    }

    /**
     * 处理接口方法错误异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result TokenExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return Result.failure("请求方法类型错误", e.getMessage());
    }

    /**
     * 处理所有从Controller中抛出的异常
     *
     * @param t 异常
     * @return 错误结果
     */
    @ExceptionHandler(Throwable.class)
    public Result baseErrorHandler(Throwable t) {
        log.error(t.getMessage());
        return Result.failure(t.getMessage());
    }
}
