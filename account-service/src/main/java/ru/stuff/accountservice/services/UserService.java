package ru.stuff.accountservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stuff.accountservice.dtos.auth.AuthRequest;
import ru.stuff.accountservice.dtos.auth.ClaimsResponse;
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

    //TODO Подумать над тем какую роль из какого репозитория добавить
    public ResponseEntity<ClaimsResponse> sendClaims(AuthRequest authRequest){
        if(userRepository.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword()).isPresent()){
            User user = userRepository.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword()).get();

            ClaimsResponse claimsResponse = new ClaimsResponse(user.getRole().getTag());
            return ResponseEntity.ok(claimsResponse);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
