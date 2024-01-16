package ru.stuff.authservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stuff.authservice.dtos.gateway.AuthRequest;
import ru.stuff.authservice.dtos.gateway.AuthResponse;
import ru.stuff.authservice.dtos.gateway.RefreshRequest;
import ru.stuff.authservice.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest){
        return authService.refresh(refreshRequest);
    }
}
