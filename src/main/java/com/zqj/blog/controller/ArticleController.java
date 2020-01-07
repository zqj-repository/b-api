package com.zqj.blog.controller;

import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.view.ArticleListItem;
import com.zqj.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> readArticle(@PathVariable("id") Integer id) {
        Article article = articleService.getArticle(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @GetMapping("/all")
    public List<ArticleListItem> allArticles() {
        return articleService.getArticles();
    }

}
