package ru.stuff.accountservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stuff.accountservice.dtos.auth.AuthRequest;
import ru.stuff.accountservice.dtos.auth.ClaimsResponse;
import ru.stuff.accountservice.models.User;
import ru.stuff.accountservice.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/edit")
public class EditorController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequest authRequest){
        return userService.createUser(authRequest);
    }
}
