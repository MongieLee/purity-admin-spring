package com.example.demo.model.queryUtil;

import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

@Data
public class TodayNewsQuery extends BaseQuery {
    private String title;
    private Boolean isPublish;
    private Date createdStar;
    private Date createdEnd;
}

