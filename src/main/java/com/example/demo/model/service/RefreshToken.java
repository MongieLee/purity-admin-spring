package com.example.demo.model.service;

import com.example.demo.model.GrantTypeDeserializer;
import com.example.demo.valid.CustomEnumValidator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "刷新令牌对象")
public class RefreshToken {
    @NotBlank(message = "refreshToken字段不能为空")
    @ApiModelProperty(value = "刷新token的凭证", required = true)
    private String refreshToken;

    @JsonDeserialize(using = GrantTypeDeserializer.class)
    @ApiModelProperty(value = "类型", required = true)
    @CustomEnumValidator(value = TokenGrantType.class, message = "tokenGrantType字段应该为refresh_token", expectValue = "refresh_token")
    private TokenGrantType tokenGrantType;

    public RefreshToken() {
        System.out.println(1);
    }

    public enum TokenGrantType {
        ACCESS_TOKEN("access_token"), REFRESH_TOKEN("refresh_token");

        private String type;

        TokenGrantType(String type) {
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