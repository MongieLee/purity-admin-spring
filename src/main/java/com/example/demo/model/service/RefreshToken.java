package com.example.demo.model.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "刷新令牌对象")
public class RefreshToken {
    //    @EnumValidator(value = GrantType.class, message = "grant_type类型错误")
    //    @JsonDeserialize(using = GrantTypeDeserializer.class)
    // TODO Springboot默认处理了enum类型的反序列化，异常的枚举值校验未处理
    @ApiModelProperty(value = "类型", required = true)
    private GrantType grantType;

    @NotBlank(message = "刷新token的凭证不能为空")
    @NotNull(message = "刷新token的凭证不能为空")
    @ApiModelProperty(value = "刷新token的凭证", required = true)
    private String refreshToken;

    public enum GrantType {
        TOKEN("token"),
        REFRESH_TOKEN("REFRESH_TOKEN");
        private final String type;

        GrantType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }
}
//
//class GrantTypeDeserializer extends JsonDeserializer<GrantType> {
//    @Override
//    public GrantType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//        String text = jsonParser.getText(); // 获取json中的值
//        JsonStreamContext parsingContext = jsonParser.getParsingContext(); // 获取当前解析器的解析上下文
//        String currentName = jsonParser.getCurrentName(); // 获取当前字段名称
//        Object currentValue = jsonParser.getCurrentValue(); // 接受的body对象
//        try {
//            Field declaredField = currentValue.getClass().getDeclaredField(currentName); // 获取当前字段
//            Class<?> type = declaredField.getType();
//            Object create = type.getDeclaredMethod("create", Object.class).invoke(null, text);
//            return (GrantType) create;
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}