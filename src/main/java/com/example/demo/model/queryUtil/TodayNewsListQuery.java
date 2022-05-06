package com.example.demo.model.queryUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(description = "今日资讯列表分页查询实体类")
public class TodayNewsListQuery extends BaseListQuery {
    @ApiModelProperty("资讯标题")
    private String title;

    @ApiModelProperty("是否发布")
    private Boolean isPublish;

    @ApiModelProperty("创建起始时间（需要配合createdEnd）")
    private Date createdStar;

    @ApiModelProperty("创建结束时间（需要配合createdStar）")
    private Date createdEnd;
}

