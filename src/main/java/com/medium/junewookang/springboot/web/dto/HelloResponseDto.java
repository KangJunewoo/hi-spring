package com.medium.junewookang.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 롬복 - 모든 필드에 getter 생성
@RequiredArgsConstructor // 롬복 - 모든 final 필드가 들어간 생성자 생성
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
