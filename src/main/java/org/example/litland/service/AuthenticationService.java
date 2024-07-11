package org.example.litland.service;

import lombok.RequiredArgsConstructor;
import org.example.litland.auth.SignInRequest;
import org.example.litland.auth.SignUpRequest;
import org.example.litland.model.Role;
import org.example.litland.repository.UserRepository;
import org.example.litland.response.AuthResponse;
import org.example.litland.shell.BookCartShell;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.example.litland.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;
    private final UserRepository userRepository;

    public AuthResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .isBlocked(false)
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt, new ArrayList<>(), "SUCCESS", "");
    }

    public AuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user;

        try {
            user = userService
                    .userDetailsService()
                    .loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            return new AuthResponse(null, new ArrayList<>(), "USER_NOT_FOUND", "Не верный логин или пароль");
        }

        var jwt = jwtService.generateToken(user);
        User userDB = userRepository.findByUsername(request.getUsername()).get();
        return new AuthResponse(jwt, cartService.getAllByUser(String.valueOf(userDB.getId())), "SUCCESS", "");
    }
}
