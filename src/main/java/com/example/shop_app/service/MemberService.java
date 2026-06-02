package com.example.shop_app.service;

import com.example.shop_app.domain.Member;
import com.example.shop_app.exception.MemberNotFoundException;
import com.example.shop_app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        if (memberId == null) {
            throw new MemberNotFoundException();
        }
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
