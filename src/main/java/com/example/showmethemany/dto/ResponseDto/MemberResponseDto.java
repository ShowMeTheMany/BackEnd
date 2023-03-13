package com.example.showmethemany.dto.ResponseDto;

import com.example.showmethemany.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// 기능 : 로그인한 유저 정보 Response Dto
@Getter
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;

    public MemberResponseDto(Member member){
        this.memberId  = member.getId();
        this.email     = member.getEmail();
    }
}
