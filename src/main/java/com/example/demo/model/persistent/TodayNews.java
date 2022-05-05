package com.example.demo.model.persistent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 菜单实体类
 */
@ApiModel(description = "今日资讯实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TodayNews {
    @ApiModelProperty("唯一主键Id")
    private Long id;

    @NotNull(message = "标题不能为空")
    @NotBlank(message = "标题不能为空")
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