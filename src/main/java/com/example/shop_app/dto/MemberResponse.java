package com.example.shop_app.dto;

import com.example.shop_app.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    
    private Long id;
    private String email;
    private String nickname;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(), 
                member.getEmail(), 
                member.getNickname()
        );
    }
}
