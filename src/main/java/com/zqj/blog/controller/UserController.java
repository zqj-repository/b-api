package com.zqj.blog.controller;

import com.zqj.blog.entity.bo.LoginUser;
import com.zqj.blog.entity.bo.NewUser;
import com.zqj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Validated NewUser newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
//        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate/{id}/{signal}")
    public ResponseEntity<?> activate(@PathVariable("id") Integer userId, @PathVariable("signal") String signal) {
        userService.activateUser(userId, signal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginUser loginUser) {
        return ResponseEntity.ok(userService.login(loginUser));
    }

}
