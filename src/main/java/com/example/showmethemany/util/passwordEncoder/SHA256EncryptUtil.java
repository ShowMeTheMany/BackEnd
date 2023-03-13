package com.example.showmethemany.util.passwordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA256EncryptUtil {

    public static String ShaEncoder(String userPw) {
        try {
            //알고리즘은 SHA256으로 하여 MessageDigest 객체 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //해시된 데이터는 바이트 배열의 바이너리 데이터임.
            byte[] hash = digest.digest(userPw.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            //바이트 배열을 16진수(hex) 문자열로 변환
            for (byte b : hash) {
                //byte 8비트 ->int 32bit 형변환 시 앞의 18비트가 19번째 비트와 같은 값으로 채우는데
                //이 경우에 원본 값과 다른 경우가 되는 것을 방지하기 위한 연산
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static byte[] shaEncoderByte(String message){
//이 메소드는 바이트배열을 16진수 문자열로 변환하지 않음
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            return hash;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }
}