package com.example.demo.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/12/1 13:44
 */
@Data
@Accessors(chain = true)
public class Common {
    private Long id;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
