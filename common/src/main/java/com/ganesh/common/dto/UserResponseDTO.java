package com.ganesh.common.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String userName;
    private String lastname;
}
