package org.example.litland.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.litland.auth.SignInRequest;
import org.example.litland.auth.SignUpRequest;
import org.example.litland.response.AuthResponse;
import org.example.litland.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    @CrossOrigin(origins = "http://localhost:3000")
    public AuthResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    @CrossOrigin(origins = "http://localhost:3000")
    public AuthResponse signIn(@RequestBody SignInRequest request) {
        System.out.println(request.getUsername());
        return authenticationService.signIn(request);
    }

    @GetMapping("/logout")
    @CrossOrigin(origins = "http://localhost:3000")
    public AuthResponse logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return new AuthResponse(null, new ArrayList<>(), "SUCCESS", "");
        } else {
            return new AuthResponse(null, new ArrayList<>(), "NOT_AUTHENTICATED", "Пользователь не авторизован");
        }
    }
}
