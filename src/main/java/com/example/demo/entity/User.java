package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true) // 生成的setter方法返回当前this
@Builder
public class User {
    private String id;
    private String username;
    @JsonIgnore
    private String encryptedPassword;
    private Instant createdAt;
    private Instant updatedAt;
    private String avatar;
}