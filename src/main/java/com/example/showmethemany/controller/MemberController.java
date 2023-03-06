package com.example.showmethemany.controller;

import com.example.showmethemany.Service.MemberService;
import com.example.showmethemany.dto.RequestDto.SignUpRequestDto;
import com.example.showmethemany.dto.ResponseDto.MemberResponseDto;
import com.example.showmethemany.util.globalResponse.GlobalResponseDto;
import com.example.showmethemany.util.globalResponse.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.example.showmethemany.util.globalResponse.code.StatusCode.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/auth/signUp")
    public ResponseEntity<GlobalResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
        return ResponseUtil.response(OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody SignUpRequestDto signUpRequestDto,
                                                   HttpServletResponse httpServletResponse) {
        return ResponseUtil.response(memberService.login(signUpRequestDto, httpServletResponse));
    }
}
