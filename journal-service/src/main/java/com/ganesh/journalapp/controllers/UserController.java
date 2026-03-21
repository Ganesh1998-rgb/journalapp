package com.ganesh.journalapp.controllers;

import com.ganesh.journalapp.apiResponse.WheatherApiResponse;
import com.ganesh.journalapp.entity.User;
import com.ganesh.journalapp.service.UserService;
import com.ganesh.journalapp.service.WheatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "User APIs", description = "Operations related to user management and weather info")
public class UserController {

    private final UserService userService;
    private final WheatherService wheatherService;

    @Operation(summary = "Update user", description = "Update existing user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PutMapping
    public ResponseEntity<User> updateUser(
            @Parameter(description = "Updated user object", required = true)
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @Operation(summary = "Delete user", description = "Delete user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Username of the user to delete", required = true)
            @PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Greeting with weather", description = "Returns greeting message with current weather info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Greeting fetched successfully")
    })
    @GetMapping
    public ResponseEntity<?> gretting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        WheatherApiResponse wheatherApiResponse = wheatherService.getWheather("Mumbai");
        String gretting = "";

        if (wheatherApiResponse != null) {
            gretting = "Weather feels like " + wheatherApiResponse.getCurrent().getFeelslike();
            return ResponseEntity.ok(gretting);
        }

        return ResponseEntity.ok("Hi " + username);
    }

    @Operation(summary = "Get weather by city", description = "Fetch weather details for a given city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather data retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/weather/{city}")
    public WheatherApiResponse getWeather(
            @Parameter(description = "City name to fetch weather for", example = "Mumbai", required = true)
            @PathVariable String city) {
        return wheatherService.getWeather(city);
    }
}