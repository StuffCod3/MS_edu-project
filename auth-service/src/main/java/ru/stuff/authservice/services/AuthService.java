package ru.stuff.authservice.services;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stuff.authservice.dtos.gateway.AuthRequest;
import ru.stuff.authservice.dtos.gateway.AuthResponse;
import ru.stuff.authservice.dtos.gateway.RefreshRequest;
import ru.stuff.authservice.utils.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtTokenUtil jwtTokenUtil;

    public ResponseEntity<AuthResponse> login(AuthRequest authRequest){
        ResponseEntity<Claims> claimsUser = restTemplate.postForEntity("http://account-service/api/v1/get_user_claims", authRequest, Claims.class);

        if (claimsUser.getStatusCode() == HttpStatus.OK){
            Claims claims = claimsUser.getBody();

            String accessToken = jwtTokenUtil.generateAccessToken(authRequest.getEmail(), claims);
            String refreshToken = jwtTokenUtil.generateRefreshToken(authRequest.getEmail());

            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
            return ResponseEntity.ok(authResponse);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<AuthResponse> refresh(RefreshRequest refreshRequest){
        if (jwtTokenUtil.validateRefreshToken(refreshRequest.getRefreshToken())){
            Claims claims = jwtTokenUtil.getRefreshClaims(refreshRequest.getRefreshToken());

            String newAccessToken = jwtTokenUtil.generateAccessToken(claims.getSubject(), claims);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(claims.getSubject());

            AuthResponse authResponse = new AuthResponse(newAccessToken, newRefreshToken);
            return ResponseEntity.ok(authResponse);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
