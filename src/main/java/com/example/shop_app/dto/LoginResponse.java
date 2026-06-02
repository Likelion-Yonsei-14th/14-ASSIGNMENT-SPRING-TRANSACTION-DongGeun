package com.example.shop_app.dto;

import com.example.shop_app.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    
    private String accessToken;
    private String tokenType;
    private Long memberId;
    private String nickname;

    public static LoginResponse of(String accessToken, Member member) {
        return new LoginResponse(
                accessToken, 
                "Bearer", 
                member.getId(), 
                member.getNickname()
        );
    }
}
