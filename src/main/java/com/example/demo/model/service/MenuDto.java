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
    private String path;
    private Long parentId;
    private String parentName;
    private Integer sequence;
    private Instant createdAt;
    private Instant updatedAt;
    private List<MenuDto> children;
}