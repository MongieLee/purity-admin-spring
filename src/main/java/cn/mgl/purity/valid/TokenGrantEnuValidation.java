package cn.mgl.purity.valid;

import cn.mgl.purity.model.service.RefreshToken;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Constraint注解validatedBy指定校验实现类
 */
public class TokenGrantEnuValidation implements ConstraintValidator<CustomEnumValidator, Object> {
    private Boolean allowNull;
    private String expectValue;

    public TokenGrantEnuValidation() {
    }

    @Override
    public void initialize(CustomEnumValidator annotation) {
        expectValue = annotation.expectValue();
        allowNull = annotation.allowNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (allowNull) {
            return true;
        }
        return Objects.nonNull(value) &&
                (value instanceof RefreshToken.TokenGrantType) &&
                (expectValue.equals(((RefreshToken.TokenGrantType) value).getType()));
    }
}
