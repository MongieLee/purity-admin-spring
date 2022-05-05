package com.example.demo.model.service;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "登录注册实体类")
@Data
public class Account {
    @NotBlank(message = "用户名不能为空")
    @NotNull(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    private String password;

    private String avatar;
}