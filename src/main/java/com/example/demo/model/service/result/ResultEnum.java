package com.example.demo.model.service.result;

/**
 * 状态枚举定义
 */
public enum ResultEnum {
    SUCCESSFUL("success"),
    FAILURE("failure");

    private String status;

    public String getStatus() {
        return status;
    }

    ResultEnum(String status) {
        this.status = status;
    }
}