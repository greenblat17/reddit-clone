package com.greenblat.redditclone.controller;

import com.greenblat.redditclone.dto.AuthenticationResponse;
import com.greenblat.redditclone.dto.LoginRequest;
import com.greenblat.redditclone.dto.RegisterRequest;
import com.greenblat.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = authService.signup(registerRequest);
        return new ResponseEntity<>("User register successful. \n Response: " + authenticationResponse, HttpStatus.OK);
    }

    @GetMapping("/account_verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        System.out.println("hi");
        return authService.login(loginRequest);
    }
}
