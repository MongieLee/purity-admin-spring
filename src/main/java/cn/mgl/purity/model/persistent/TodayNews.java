package cn.mgl.purity.model.persistent;

import cn.mgl.purity.valid.TodayNewsModelValid;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 今日资讯实体类
 */
@ApiModel(description = "今日资讯实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Accessors(chain = true)
public class TodayNews {
    @ExcelProperty(value = "Id")
    @ApiModelProperty("唯一主键Id")
    @Null(groups = TodayNewsModelValid.Create.class)
    @NotNull(groups = TodayNewsModelValid.Update.class, message = "资讯Id不能为空")
    private Long id;

    @ExcelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    @ApiModelProperty("今日资讯标题")
    private String title;

    @ExcelIgnore
    @ApiModelProperty("资讯封面图片地址")
    private String coverImg;

    @ExcelProperty(value = "资讯内容")
    @ApiModelProperty("资讯文章内容")
    private String content;

    @ExcelIgnore
    @ExcelProperty(value = "是否发布")
    @ApiModelProperty("是否发布,true为发布，false为未发布")
    private Boolean isPublish;

    @ExcelIgnore
    @ApiModelProperty("排序号")
    private Integer sequence;

    @ExcelProperty("创建人")
    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private Date createdAt;

    @ExcelProperty("更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;

    @ApiModelProperty("更新时间")
    @ExcelProperty("更新时间")
    private Date updatedAt;

    @ExcelProperty("发布人")
    @ApiModelProperty("发布人")
    private String publishedBy;

    @ExcelProperty("发布时间")
    @ApiModelProperty("发布时间")
    private Date publishedAt;
}
