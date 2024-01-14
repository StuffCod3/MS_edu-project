package ru.stuff.orderservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/{id}")
    private String sendOrdersUser(@PathVariable int id){

        return "User " + id;
    }
}
