package com.example.demo.model.persistent;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class RoleDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Boolean state;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
    private List<Long> permissionRoles;
}
