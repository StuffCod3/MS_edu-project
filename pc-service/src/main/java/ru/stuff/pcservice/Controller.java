package ru.stuff.pcservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/test")
    private String test(){
        return "HELLO PC-SERVICE";
    }
}
