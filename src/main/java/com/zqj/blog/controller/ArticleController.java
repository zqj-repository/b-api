package com.zqj.blog.controller;

import com.zqj.blog.entity.Article;
import com.zqj.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<?> writeArticle(@RequestBody Article article) {
        articleService.publishArticle(article);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public Article readArticle(@PathVariable("id") Integer id) {
        return articleService.getArticle(id);
    }

}
