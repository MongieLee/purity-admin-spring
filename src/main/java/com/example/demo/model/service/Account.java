package com.example.demo.model.service;

import com.example.demo.model.GrantTypeDeserializer;
import com.example.demo.valid.AccountModelValid;
import com.example.demo.valid.CustomEnumValidator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @NotBlank(groups = AccountModelValid.Login.class, message = "用户名不能为空")
    @Size(groups = AccountModelValid.Register.class, min = 6, max = 16, message = "账号长度需要在6-16位")
    @ApiModelProperty(required = true, example = "test001", value = "账号")
    private String username;

    @NotBlank(groups = AccountModelValid.Login.class, message = "密码不能为空")
    @Size(groups = AccountModelValid.Register.class, min = 6, max = 16, message = "密码长度需要在6-16位")
    @ApiModelProperty(required = true, example = "123456", value = "密码")
    private String password;

    private String avatar;

    @JsonDeserialize(using = GrantTypeDeserializer.class)
    @CustomEnumValidator(groups = AccountModelValid.Login.class, value = RefreshToken.TokenGrantType.class, message = "tokenGrantType字段应该为access_token", expectValue = "access_token")
    @ApiModelProperty(value = "请求类型，登录时需要传入【access_token】")
    private RefreshToken.TokenGrantType tokenGrantType;
}