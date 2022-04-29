package com.example.demo.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 菜单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) // 生成的setter方法返回当前this
public class TodayNews {
    private Long id;
    private String title;
    private String coverImg;
    private String content;
    private Boolean isPublish;
    private Integer sequence;

    private String createdBy;
    private Instant createdAt;

    private String updatedBy;
    private Instant updatedAt;

    private Instant publishedAt;
    private String publishedBy;
}