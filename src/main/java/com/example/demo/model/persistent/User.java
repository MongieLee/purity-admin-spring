package com.example.demo.model.persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户实体类")
public class User {
    @ApiModelProperty(value = "唯一主键Id")
    private Long id;

    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(required = true, example = "test001", value = "账号")
    private String username;

    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(required = true, example = "123456", value = "密码")
    private String encryptedPassword;

    @ApiModelProperty(example = "true", value = "账号状态,true为正常，false为封号")
    private Boolean status;

    @ApiModelProperty(example = "2022-01-01 12:00:00", value = "创建时间")
    private Instant createdAt;

    @ApiModelProperty(example = "2022-01-01 12:00:00", value = "更新时间")
    private Instant updatedAt;

    @ApiModelProperty(value = "头像图片地址")
    private String avatar;
}