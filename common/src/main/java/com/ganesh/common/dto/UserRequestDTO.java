package com.ganesh.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @Schema(description = "Username of the user", example = "ganesh123")
    @NotBlank(message = "Username is required")
    private String userName;

    @Schema(description = "Password of the user", example = "password@123")
    @NotBlank(message = "Password is required")
    private String password;

}
