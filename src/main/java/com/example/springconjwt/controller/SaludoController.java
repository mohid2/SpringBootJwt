package com.example.springconjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SaludoController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello from endpoint no protected";
    }

    @GetMapping("/helloprotected")
    public String Helloprotected(){
        return "Hello from endpoint protected";
    }

}
