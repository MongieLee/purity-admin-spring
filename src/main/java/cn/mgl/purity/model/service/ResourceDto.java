package cn.mgl.purity.model.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

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
    private Date createdAt;
    private Date updatedAt;
}
