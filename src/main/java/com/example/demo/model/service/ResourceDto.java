package com.example.demo.model.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

/**
 * 资源Dto实体类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@AllArgsConstructor
public class ResourceDto {
    private Long id;
    private String name;
    private String url;
    private String description;
    private Long categroyId;
    private String categroyName;
    private Instant createdAt;
    private Instant updatedAt;
}