package com.example.demo.model.persistent;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

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
    private Long parentId;
    private String path;
    private String icon;
    private Character menuType;
    private Integer sequence;
    private Boolean visible;
    private String remark;
    private String permission;
    private String createBy;
    private Boolean state;
    private Boolean isLink;
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private String updatedBy;
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
