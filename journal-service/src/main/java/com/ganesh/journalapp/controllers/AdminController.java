package com.ganesh.journalapp.controllers;


import com.ganesh.common.dto.UserResponseDTO;
import com.ganesh.journalapp.entity.User;
import com.ganesh.journalapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin APIs", description = "Operations related to admin management")
public class AdminController {

    private final UserService userService;

    @Operation(summary = "Get all users", description = "Fetch all registered users from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> all = userService.getAll();

        if (all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create admin user", description = "Create a new admin user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin user created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create-admin-user")
    public ResponseEntity<User> createUser(
            @Parameter(description = "User object to be created", required = true)
            @RequestBody User user) {

        User admin = userService.createAdmin(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }
}