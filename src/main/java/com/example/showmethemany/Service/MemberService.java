package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.MemberRepository;
import com.example.showmethemany.domain.Address;
import com.example.showmethemany.domain.Member;
import com.example.showmethemany.dto.RequestDto.SignUpRequestDto;
import com.example.showmethemany.util.passwordEncoder.SHA256EncryptUtil;
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
        String password = SHA256EncryptUtil.ShaEncoder(signUpRequestDto.getPassword());
        String name = signUpRequestDto.getName();

        Address address = new Address(signUpRequestDto.getCity(), signUpRequestDto.getStreet(), signUpRequestDto.getZipcode());

        Member member = new Member(loginId,name,address,email,password);
        memberRepository.save(member);
    }

    @Transactional
    public void login(SignUpRequestDto signUpRequestDto, HttpServletResponse httpServletResponse) {
        String loginId = signUpRequestDto.getLoginId();
        String password = SHA256EncryptUtil.ShaEncoder(signUpRequestDto.getPassword());

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );

        if (!member.getPassword().equals(password)) {
            throw new CustomException(BAD_REQUEST);
        }
        sessionManager.createSession(signUpRequestDto, httpServletResponse);
    }
}
