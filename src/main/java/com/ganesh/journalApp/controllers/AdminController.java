package com.ganesh.journalApp.controllers;

import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAll(){
        List<User> all = userService.getAll();
        if(all!=null && !all.isEmpty()){
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User admin = userService.createAdmin(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }

}
