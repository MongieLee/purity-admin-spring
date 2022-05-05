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
 * 今日资讯实体类
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
    @ApiModelProperty("今日资讯标题")
    private String title;

    @ApiModelProperty("资讯封面图片地址")
    private String coverImg;

    @ApiModelProperty("资讯文章内容")
    private String content;

    @ApiModelProperty("是否发布,true为发布，false为未发布")
    private Boolean isPublish;

    @ApiModelProperty("排序号")
    private Integer sequence;

    @ApiModelProperty("创建人")
    private String createdBy;
    @ApiModelProperty("创建时间")
    private Instant createdAt;

    @ApiModelProperty("更新人")
    private String updatedBy;
    @ApiModelProperty("更新时间")
    private Instant updatedAt;

    @ApiModelProperty("发布人")
    private String publishedBy;
    @ApiModelProperty("发布时间")
    private Instant publishedAt;
}
