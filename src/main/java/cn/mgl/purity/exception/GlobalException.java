package cn.mgl.purity.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import cn.mgl.purity.model.service.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public JsonResult paramExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!allErrors.isEmpty()) {
                // 可能有多个校验异常，取第一个异常信息即可
                return JsonResult.failure(allErrors.get(0).getDefaultMessage());
            }
        }
        return JsonResult.failure("请求失败");
    }

    /**
     * 处理Post请求的参数解析异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult missingRequestBodyExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return JsonResult.failure("请求体参数解析失败，请检查接口传参是否正确", e.getMessage());
    }

    /**
     * 处理JWT校验身份异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JWTVerificationException.class)
    public JsonResult TokenExceptionHandler(JWTVerificationException e) {
        log.error(e.getMessage());
        return JsonResult.failure("token校验失败，请检查是否正确或是否过期", HttpStatus.UNAUTHORIZED.value());
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
    public JsonResult TokenExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return JsonResult.failure("请求方法类型错误", e.getMessage());
    }

    /**
     * 处理所有从Controller中抛出的异常
     *
     * @param t 异常
     * @return 错误结果
     */
    @ExceptionHandler(Throwable.class)
    public JsonResult baseErrorHandler(Throwable t) {
        log.error("出现异常");
        log.error(t.getMessage());
        t.printStackTrace();
        return JsonResult.failure(t.getMessage());
    }
}
