package com.zqj.blog.controller.admin;

import com.zqj.blog.entity.Article;
import com.zqj.blog.entity.bo.ArticleFormBO;
import com.zqj.blog.service.ArticleService;
import com.zqj.blog.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<?> postArticle(@RequestBody @Validated ArticleFormBO postArticle) {
        Article article = postArticle.toPO();
        articleService.createArticle(article);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateArticle(@RequestBody @Validated ArticleFormBO postArticle) {
        Article article = postArticle.toPO();
        Validations.expect(article != null && article.getId() != null);

        articleService.updateArticle(article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Integer id) {
        articleService.removeArticle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticle() {
        return ResponseEntity.ok(articleService.getAllArticle());
    }
}
