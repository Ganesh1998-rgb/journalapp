package com.ganesh.journalapp.controllers;


import com.ganesh.common.dto.UserRequestDTO;
import com.ganesh.common.dto.UserResponseDTO;
import com.ganesh.journalapp.service.CustomeUserDetailsService;
import com.ganesh.journalapp.service.UserService;
import com.ganesh.journalapp.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Public APIs", description = "Public endpoints (no authentication required)")
public class PublicController {

    private final AuthenticationManager authenticationManager;
private final UserService userService;
private final CustomeUserDetailsService userDetailsService;
private final JWTUtils jwtUtils;

    @Operation(
            summary = "Register new user",
            description = "Create a new user account (public endpoint)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user input")
    })
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> singUp(
            @Parameter(description = "User details to register", required = true)
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.saveNewUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);

    }


    @Operation(
            summary = "Login for user",
            description = "User login endpoint  (public endpoint)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user input")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "User details to register", required = true)
            @RequestBody UserRequestDTO userRequestDTO) {
        try{
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(), userRequestDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDTO.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
           log.error("Exception occured while createAuthenticatin token "+e);
           return new ResponseEntity("Incorrect Username and pasword", HttpStatus.BAD_REQUEST);
        }

    }

}