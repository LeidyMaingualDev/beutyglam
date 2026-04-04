package com.beutyglam.dto.auth;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String message;
    private String token;
}
