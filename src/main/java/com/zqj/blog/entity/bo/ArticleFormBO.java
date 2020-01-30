package com.zqj.blog.entity.bo;

import com.zqj.blog.entity.Article;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class ArticleFormBO {

    private Integer id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @Positive
    private Integer category;

    @NotEmpty
    private String action;

    public Article toPO() {
        Article article = Article.builder()
                .id(id)
                .title(title)
                .content(content).build();
        article.setStatus(ArticleStatus.DRAFT.getCode().equals(action) ? ArticleStatus.DRAFT.getCode() : ArticleStatus.PUBLISHED.getCode());
        return article;
    }

}
