package com.example.demo.exception;

import com.example.demo.model.service.result.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result paramExceptionHandler(MethodArgumentNotValidException e) {
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

    @ExceptionHandler(Throwable.class)
    public Result baseErrorHandler(Throwable t) {
        return Result.failure(t.getMessage());
    }
}
