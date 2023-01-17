package cn.mgl.purity.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = TokenGrantEnuValidation.class)
public @interface CustomEnumValidator {
    Class<?> value();

    // 默认允许为空
    boolean allowNull() default false;

    String message() default "入参值不是正确的枚举类型";

    String expectValue();

    String method() default "getCode";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

//    @Slf4j
//    class EnumValidatorHandle implements ConstraintValidator<CustomEnumValidator, Object>, Annotation {
//        private List<Object> values = new ArrayList<>();
//
//        @Override
//        public void initialize(CustomEnumValidator customEnumValidator) {
//            Class<?> clz = customEnumValidator.value();
//            Object[] objects = clz.getEnumConstants();
//            try {
//                Method method = clz.getMethod(customEnumValidator.method());
//                if (Objects.isNull(method)) {
//                    throw new CustomException(String.format("枚举对象%s缺少名为%s的方法", clz.getName(), customEnumValidator.method()));
//                }
//                Object value;
//                for (Object obj : objects) {
//                    value = method.invoke(obj);
//                    values.add(value);
//                }
//            } catch (Exception e) {
//                log.error("处理枚举校验异常:{}", e);
//            }
//        }
//
//        @Override
//        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
//            if (value instanceof String) {
//                String valueStr = (String) value;
//                return StringUtils.isEmpty(valueStr) || values.contains(value);
//            }
//            // 判断是否枚举类型
//            if (value.getClass().isEnum()) {
//                return true;
//            }
//            return Objects.isNull(value) || values.contains(value);
//        }
//
//        @Override
//        public Class<? extends Annotation> annotationType() {
//            return null;
//        }
//    }

}
