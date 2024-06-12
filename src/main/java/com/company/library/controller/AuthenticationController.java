package com.company.library.controller;

import com.company.library.dto.request.AuthenticationRequest;
import com.company.library.dto.request.RegisterRequest;
import com.company.library.dto.response.AuthenticationResponse;
import com.company.library.model.base.BaseResponse;
import com.company.library.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    public BaseResponse<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request){
        return BaseResponse.success(service.register(request));
    }

    @PostMapping("/authenticate")
    public BaseResponse<AuthenticationResponse> authenticate(@Valid@RequestBody AuthenticationRequest request){
        return BaseResponse.success(service.authenticate(request));
    }



}
