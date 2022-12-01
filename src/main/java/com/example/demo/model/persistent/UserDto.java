package com.example.demo.model.persistent;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.TypeHandler;

import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 生成的setter方法返回当前this
public class UserDto {
    private Long id;
    private String username;
    private String nickname;
    private Boolean status;
    private Date createdAt;
    private Date updatedAt;
    private String avatar;
//    private UserTypeEnum userType;
    private String roleNames;
    private List<Long> roleIds;
}
