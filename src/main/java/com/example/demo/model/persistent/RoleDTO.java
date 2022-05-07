package com.example.demo.model.persistent;

import lombok.Data;

import java.util.Date;

@Data
public class RoleDTO {

    private Long id;
    private String name;
    private String code;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;

}
