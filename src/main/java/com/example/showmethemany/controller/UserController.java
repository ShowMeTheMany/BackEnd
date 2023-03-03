package com.example.showmethemany.controller;

import com.example.showmethemany.dto.RequestDto.DeleteUserRequestDto;
import com.example.showmethemany.dto.RequestDto.SignUpRequestDto;
import com.example.showmethemany.util.globalResponse.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
//    @PostMapping(value = "/auth/signup")
//    public ResponseEntity<GlobalResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
//    }
//
//    @PostMapping(value = "/auth/login")
//    public ResponseEntity<GlobalResponseDto> login(@RequestBody SignUpRequestDto signUpRequestDto) {
//    }
//
//    @PostMapping(value = "/auth/deleteUser")
//    public ResponseEntity<GlobalResponseDto> deleteUser(@RequestBody DeleteUserRequestDto deleteUserRequestDto) {
//    }
}
