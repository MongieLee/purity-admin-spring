package com.example.demo.model.queryUtil;

import lombok.Data;

import java.time.Instant;

@Data
public class TodayNewsQuery extends BaseQuery {
    private String title;
    private Boolean isPublish;
    private Instant createdStar;
    private Instant createdEnd;
}

