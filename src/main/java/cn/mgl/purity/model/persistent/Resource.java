package cn.mgl.purity.model.persistent;

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
@Accessors(chain = true)
@ToString
public class Resource {
    private Long id;
    private String name;
    private String url;
    private String description;
    private Long categroyId;
    private Date createdAt;
    private Date updatedAt;
}
