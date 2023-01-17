package cn.mgl.purity.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MyTest {
    private Long id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
