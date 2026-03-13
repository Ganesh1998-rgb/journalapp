package com.ganesh.journalApp.controllers;

import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

private final UserService userService;

@PutMapping
public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
}

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
       userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}

