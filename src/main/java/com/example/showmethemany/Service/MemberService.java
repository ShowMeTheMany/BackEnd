package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.MemberRepository;
import com.example.showmethemany.domain.Member;
import com.example.showmethemany.dto.RequestDto.SignUpRequestDto;
import com.example.showmethemany.dto.ResponseDto.MemberResponseDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import com.example.showmethemany.util.globalResponse.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.example.showmethemany.util.globalResponse.code.StatusCode.*;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String email = signUpRequestDto.getEmail();
        String loginId = signUpRequestDto.getLoginId();
        String nickname = signUpRequestDto.getNickname();
        String password = signUpRequestDto.getPassword();

        Member member = new Member(loginId,nickname,email,password);
        memberRepository.save(member);
    }

    @Transactional
    public MemberResponseDto login(SignUpRequestDto signUpRequestDto, HttpServletResponse httpServletResponse) {
        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );

        if (!member.getPassword().equals(password)) {
            throw new CustomException(BAD_REQUEST);
        }
        sessionManager.createSession(signUpRequestDto, httpServletResponse);
        return new MemberResponseDto(member);
    }
}
