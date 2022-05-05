package com.example.demo.exception;

import com.example.demo.model.service.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result paramExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!allErrors.isEmpty()) {
                FieldError error = (FieldError) allErrors.get(0);
                return Result.failure(error.getDefaultMessage());
            }
        }
        return Result.failure("请求失败");
    }

    /**
     * 处理Post请求的参数解析异常
     *
     * @param e
     * @return Result
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result missingRequestBodyExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return Result.failure("参数解析失败，请检查接口传参是否正确");
    }

    /**
     * 处理所有从Controller中抛出的异常
     *
     * @param t
     * @return Result
     */
    @ExceptionHandler(Throwable.class)
    public Result baseErrorHandler(Throwable t) {
        log.error(t.getMessage());
        return Result.failure(t.getMessage());
    }
}
