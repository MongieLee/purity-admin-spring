package com.example.demo.model.persistent;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTypeEnum {
    ADMIN("管理员", 10001),
    DEFAULT_USER("普通用户", 10002);

    String type;
    Integer code;

    UserTypeEnum(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }
}