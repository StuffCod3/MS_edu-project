package ru.stuff.userservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
public class MainController {

    private RestTemplate restTemplate;

    @GetMapping("/user/{id}")
    private String getUserOrders(@PathVariable int id){
        ResponseEntity<String> response = restTemplate.getForEntity("http://order-service/{id}", String.class, id);
        return response.getBody();
    }
}
