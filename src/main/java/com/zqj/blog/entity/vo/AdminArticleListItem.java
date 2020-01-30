package com.zqj.blog.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AdminArticleListItem {

    private Integer id;
    private String title;
    private String categoryName;
    private Integer viewCount;
    private Integer commentCount;
    private Date publishTime;
    private Date lastModify;

}
