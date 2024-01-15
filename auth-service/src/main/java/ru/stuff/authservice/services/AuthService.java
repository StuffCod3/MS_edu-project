package ru.stuff.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stuff.authservice.dtos.account.ClaimsRequest;
import ru.stuff.authservice.dtos.gateway.AuthRequest;
import ru.stuff.authservice.dtos.gateway.AuthResponse;
import ru.stuff.authservice.dtos.gateway.RefreshRequest;
import ru.stuff.authservice.utils.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtTokenUtil jwtTokenUtil;

    //TODO Сделать обработку ошибок

    public ResponseEntity<AuthResponse> login(AuthRequest authRequest){
        ResponseEntity<ClaimsRequest> claimsRequest = restTemplate.postForEntity("http://localhost:8082/api/v1/info/get_user_claims", authRequest, ClaimsRequest.class);

        if (claimsRequest.getStatusCode() == HttpStatus.OK){

            Map<String, Object> dataFromClaimsRequest = new HashMap<>();
            dataFromClaimsRequest.put("role", claimsRequest.getBody().getRole());
            Claims claims = Jwts.claims(dataFromClaimsRequest);

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
