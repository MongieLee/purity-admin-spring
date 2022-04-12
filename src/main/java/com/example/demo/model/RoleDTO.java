package com.example.demo.model;

import lombok.Data;

import java.time.Instant;

@Data
public class RoleDTO {

    private Long id;
    private String name;
    private String code;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;

}
