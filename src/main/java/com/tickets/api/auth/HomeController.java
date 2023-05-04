package com.tickets.api.auth;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping("hello")
    public String home() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "hello "+authentication.getName();
    }
}
