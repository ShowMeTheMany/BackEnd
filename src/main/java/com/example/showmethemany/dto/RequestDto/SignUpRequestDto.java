package com.example.showmethemany.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String nickname;
    private String loginId;
    private String email;
    private String password;
}
