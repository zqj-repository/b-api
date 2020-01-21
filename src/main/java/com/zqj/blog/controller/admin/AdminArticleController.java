package com.zqj.blog.controller.admin;

import com.zqj.blog.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @PostMapping
    public ResponseEntity<?> postArticle(@RequestBody @Validated Article article) {

        return null;
    }

}
