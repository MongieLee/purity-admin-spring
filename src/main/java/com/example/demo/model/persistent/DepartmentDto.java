package com.example.demo.model.persistent;

import com.example.demo.model.Common;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/12/1 13:25
 */
@Data
@Accessors(chain = true)
public class DepartmentDto extends Department {
    private List<DepartmentDto> children;
}
