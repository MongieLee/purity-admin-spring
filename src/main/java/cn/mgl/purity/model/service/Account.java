package cn.mgl.purity.model.service;

import cn.mgl.purity.valid.AccountModelValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * 登陆注册用实体类
 *
 * @author MongieLee
 */
@ApiModel(description = "登录注册实体类")
@Data
public class Account {
    @NotBlank(message = "用户名不能为空")
    @Size(groups = AccountModelValid.Register.class, min = 6, max = 16, message = "账号长度需要在6-16位")
    @ApiModelProperty(required = true, example = "test001", value = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(groups = AccountModelValid.Register.class, min = 6, max = 16, message = "密码长度需要在6-16位")
    @ApiModelProperty(required = true, example = "123456", value = "密码")
    private String password;

    private String avatar;

    @NotNull(groups = AccountModelValid.Create.class, message = "用户状态不能为空")
    private Boolean status;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(groups = AccountModelValid.Create.class, message = "昵称不能为空")
    private String nickname;

    @NotNull(groups = AccountModelValid.Create.class, message = "所属部门不能为空")
    private Long deptId;

    @NotNull(groups = AccountModelValid.Create.class, message = "角色身份不能为空")
    private ArrayList<Long> roleIds;
//
//    @JsonDeserialize(using = GrantTypeDeserializer.class)
//    @CustomEnumValidator(groups = AccountModelValid.Login.class, value = RefreshToken.TokenGrantType.class, message = "tokenGrantType字段应该为access_token", expectValue = "access_token")
//    @ApiModelProperty(value = "请求类型，登录时需要传入【access_token】")
//    private RefreshToken.TokenGrantType tokenGrantType;
}
