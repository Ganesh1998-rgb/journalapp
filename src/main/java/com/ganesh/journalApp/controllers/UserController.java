package com.ganesh.journalApp.controllers;

import com.ganesh.journalApp.apiResponse.WheatherApiResponse;
import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.service.UserService;
import com.ganesh.journalApp.service.WheatherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

private final UserService userService;
private final WheatherService wheatherService;

@PutMapping
public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
}

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
       userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

@GetMapping
    public ResponseEntity<?> gretting(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    WheatherApiResponse wheatherApiResponse = wheatherService.getWheather("Mumbai");
    String gretting="";
    if(wheatherApiResponse !=null){
gretting= "Weather feels like " + wheatherApiResponse.getCurrent().getFeelslike();
return ResponseEntity.ok(gretting);
    }

    return ResponseEntity.ok(" Hi "+authentication.getName());
    }

}

