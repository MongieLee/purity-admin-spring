package cn.mgl.purity.model.persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(required = true, example = "test001", value = "账号")
    private String username;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(required = true, example = "test001", value = "账号")
    private String nickname;

    @JsonIgnore
    @ApiModelProperty(required = true, example = "123456", value = "密码")
    private String encryptedPassword;

    @NotNull(message = "用户状态不能为空")
    @ApiModelProperty(example = "true", value = "账号状态,true为正常，false为封号")
    private Boolean status;

    @ApiModelProperty(example = "2022-01-01 12:00:00", value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(example = "2022-01-01 12:00:00", value = "更新时间")
    private Date updatedAt;

    @NotNull(message = "所属部门不能为空")
    @ApiModelProperty(example = "1", value = "部门id")
    private Long deptId;
//    private UserTypeEnum userType;

    @ApiModelProperty(value = "头像图片地址")
    private String avatar;
}
