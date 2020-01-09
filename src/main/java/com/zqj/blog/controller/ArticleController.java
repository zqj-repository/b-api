package com.zqj.blog.controller;

import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.Category;
import com.zqj.blog.entity.view.ArticleListItem;
import com.zqj.blog.service.ArticleService;
import com.zqj.blog.util.Validations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zqj.blog.util.Validations.check;

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
        articleService.createArticle(article);
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

    @PutMapping
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        check(article != null, article.getId() != null, StringUtils.isNotEmpty(article.getTitle()));

        articleService.updateArticle(article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Integer id) {
        check(id != null);

        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
