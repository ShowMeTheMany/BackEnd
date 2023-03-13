package com.example.showmethemany.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private String loginId;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
