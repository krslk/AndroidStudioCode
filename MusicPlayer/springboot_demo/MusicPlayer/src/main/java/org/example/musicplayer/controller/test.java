package org.example.musicplayer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @RequestMapping("/")
    public String hello(String name) {
        String string = "Hello World";
        if (name != null) {
            string = string + " I am " + name;
        }
        return string;
    }
}
