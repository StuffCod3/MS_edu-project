package ru.stuff.pcservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stuff.pcservice.models.PC;
import ru.stuff.pcservice.services.PcService;

import java.util.List;

@RestController
@AllArgsConstructor
public class Controller {

    private final PcService pcService;

    @GetMapping("/test")
    private String test(){
        return "HELLO PC-SERVICE";
    }

    @GetMapping("/showAll")
    private List<PC> showAll(){
        return pcService.findAll();
    }

    @GetMapping("/get_price")
    private String showAll(@RequestParam String title){
        return pcService.findByTitle(title);
    }
}
