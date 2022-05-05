package com.example.demo.model.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 登陆注册用实体类
 */
@ApiModel(description = "登录注册实体类")
@Data
public class Account {
    @NotNull(message = "用户名不能为空")
    @Size(min = 6, max = 16, message = "账号长度需要在6-16位")
    @ApiModelProperty(required = true, example = "test001", value = "账号")
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码长度需要在6-16位")
    @ApiModelProperty(required = true, example = "123456", value = "密码")
    private String password;

    private String avatar;
}