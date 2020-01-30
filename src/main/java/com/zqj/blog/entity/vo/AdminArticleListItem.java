package com.zqj.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class AdminArticleListItem {

    private Integer id;
    private String title;
    private String categoryName;
    private String status;
    private Integer viewCount;
    private Integer commentCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModify;

}
