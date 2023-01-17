package cn.mgl.purity.model.queryUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 列表分页查询通用父类
 */
@Data
@ApiModel(description = "列表分页查询通用父类")
@Accessors(chain = true)
public class BaseListQuery {
    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页数")
    private Integer pageSize;

    @ApiModelProperty("要排序的字段")
    private String orderByColumn;

    @ApiModelProperty("升序或降序")
    private Boolean isAsc;
}
