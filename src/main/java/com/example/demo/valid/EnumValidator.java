package com.example.demo.valid;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EnumValidator.EnumValidatorHandle.class)
public @interface EnumValidator {
    Class<?> value();

    String message() default "入参值不在正确枚举中";

    String method() default "getCode";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class EnumValidatorHandle implements ConstraintValidator<EnumValidator, Object>, Annotation {
        private List<Object> values = new ArrayList<>();

        @Override
        public void initialize(EnumValidator enumValidator) {
            Class<?> clz = enumValidator.value();
            Object[] objects = clz.getEnumConstants();
            try {
                Method method = clz.getMethod(enumValidator.method());
                if (Objects.isNull(method)) {
                    throw new CustomException(String.format("枚举对象%s缺少名为%s的方法", clz.getName(), enumValidator.method()));
                }
                Object value;
                for (Object obj : objects) {
                    value = method.invoke(obj);
                    values.add(value);
                }
            } catch (Exception e) {
                log.error("处理枚举校验异常:{}", e);
            }
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value instanceof String) {
                String valueStr = (String) value;
                return StringUtils.isEmpty(valueStr) || values.contains(value);
            }
            // 判断是否枚举类型
            if (value.getClass().isEnum()) {
                return true;
            }
            return Objects.isNull(value) || values.contains(value);
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }

}