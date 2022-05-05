package com.example.demo.model.queryUtil;

import lombok.Data;

import java.util.Date;

@Data
public class TodayNewsListQuery extends BaseListQuery {
    private String title;
    private Boolean isPublish;
    private Date createdStar;
    private Date createdEnd;
}

