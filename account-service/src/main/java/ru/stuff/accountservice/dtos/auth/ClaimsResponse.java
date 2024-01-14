package ru.stuff.accountservice.dtos.auth;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ClaimsResponse {
    private String role;
}
