package com.example.demo.model.persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 生成的setter方法返回当前this
public class User {
    private Long id;
    private String username;
    @JsonIgnore
    private String encrypted_password;
    private Boolean status;
    private Instant createdAt;
    private Instant updatedAt;
    private String avatar;
}