package org.nolhtaced.webapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/me")
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("/auth")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
