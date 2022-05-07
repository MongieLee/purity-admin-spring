package com.example.demo.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class ResourceCategroy {
    private Long id;
    private String name;
    private Integer sequence;
    private Date createdAt;
    private Date updatedAt;
}