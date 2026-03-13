package com.ganesh.journalApp.controllers;

import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

private final UserService userService;
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

            User saved = userService.saveNewUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

}
