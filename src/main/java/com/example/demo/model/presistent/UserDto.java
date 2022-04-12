package com.example.demo.model.presistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
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
    private Boolean status;
    private Instant createdAt;
    private Instant updatedAt;
    private String avatar;
    private String roleNames;
    private List<Long> roleIds;
}