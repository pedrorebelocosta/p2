package org.nolhtaced.webapi.controllers;

import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.core.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/me")
    public User helloUser() {
        return new User("pedrocosta", "password", "Pedro", "Costa", LocalDate.now(), true, UserRoleEnum.ADMINISTRATOR);
    }

    @GetMapping("/auth")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
