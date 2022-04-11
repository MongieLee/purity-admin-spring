package com.example.demo.model.presistent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class ResourceCategroy {
    private Long id;
    private String name;
    private Integer sequence;
    private Instant createdAt;
    private Instant updatedAt;
}