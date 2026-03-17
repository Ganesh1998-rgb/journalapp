package com.ganesh.journalApp.controllers;

import com.ganesh.journalApp.dto.UserRequestDTO;
import com.ganesh.journalApp.dto.UserResponseDTO;
import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(name = "Public APIs", description = "Public endpoints (no authentication required)")
public class PublicController {

    private final UserService userService;

    @Operation(
            summary = "Register new user",
            description = "Create a new user account (public endpoint)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user input")
    })
    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(
            @Parameter(description = "User details to register", required = true)
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.saveNewUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);

    }
}