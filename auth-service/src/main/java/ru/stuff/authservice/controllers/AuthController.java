package ru.stuff.authservice.controllers;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.stuff.authservice.dtos.gateway.AuthRequest;
import ru.stuff.authservice.dtos.gateway.AuthResponse;
import ru.stuff.authservice.dtos.gateway.RefreshRequest;
import ru.stuff.authservice.services.AuthService;
import ru.stuff.authservice.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        return authService.refresh(refreshRequest);
    }
}
