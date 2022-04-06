package com.example.demo.model.presistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * 菜单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 生成的setter方法返回当前this
@ToString
public class Menu {
    private Long id;
    private String name;
    private String path;
    private Long pId;
    private Integer sequence;
    private Instant createdAt;
    private Instant updatedAt;
}