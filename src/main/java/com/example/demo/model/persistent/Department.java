package com.example.demo.model.persistent;

import com.example.demo.model.Common;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/12/1 13:25
 */
@Data
@Accessors(chain = true)
public class Department extends Common {
    private String name;
    private Long parentId;
    private String principal;
    private String phoneNumber;
    private Integer sequence;
    private Boolean state;
}
