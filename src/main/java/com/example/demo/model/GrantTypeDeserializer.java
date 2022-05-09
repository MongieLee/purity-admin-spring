package com.example.demo.model;

import com.example.demo.model.service.RefreshToken;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * 处理前端传过来的grant_type参数，转换为对应的枚举类型，如果匹配不到对应的type，则返回null
 */
public class GrantTypeDeserializer extends JsonDeserializer<RefreshToken.TokenGrantType> {

    @Override
    public RefreshToken.TokenGrantType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object value = jsonParser.getText();
        for (RefreshToken.TokenGrantType enumConstant : RefreshToken.TokenGrantType.values()) {
            if (enumConstant.getType().equals(value)) {
                return enumConstant;
            }
        }
        return null;
    }
}


