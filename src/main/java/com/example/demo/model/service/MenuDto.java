package com.example.demo.model.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

/**
 * 菜单Dto实体类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String name;
    private Long parentId;
    private String path;
    private String icon;
    private String parentName;
    private Character menuType;
    private Integer sequence;
    private Boolean visible;
    private String remark;
    private String permission;
    private List<MenuDto> children;
    private Instant createdAt;
}