package ru.stuff.accountservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stuff.accountservice.dtos.auth.AuthRequest;
import ru.stuff.accountservice.dtos.auth.ClaimsResponse;
import ru.stuff.accountservice.models.Role;
import ru.stuff.accountservice.models.User;
import ru.stuff.accountservice.repositories.RoleRepository;
import ru.stuff.accountservice.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    //TODO Сделать обработку ошибок

    public ResponseEntity<ClaimsResponse> sendClaims(AuthRequest authRequest){
        if (userRepository.findByEmail(authRequest.getEmail()).isPresent()){
            User user = userRepository.findByEmail(authRequest.getEmail()).get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
                Role role = roleRepository.findById(user.getRole().getId()).get();
                ClaimsResponse claimsResponse = new ClaimsResponse(role.getTag());
                return ResponseEntity.ok(claimsResponse);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<User> createUser(AuthRequest authRequest) {
        if (userRepository.findByEmail(authRequest.getEmail()).isEmpty()){
            User user = new User();
            user.setEmail(authRequest.getEmail());
            user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            user.setRole(roleRepository.findByTag("ROLE_USER").get());
            return ResponseEntity.ok(userRepository.save(user));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
