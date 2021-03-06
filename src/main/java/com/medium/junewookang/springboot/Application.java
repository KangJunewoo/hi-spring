package com.medium.junewookang.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing // JPA Auditing 활성화 (createdDate, updatedDate 적용) -> SpringBootApplication과 WebMvcTest에서 중복으로 스캔되어 테스트시 문제 발생.
// 대신 config/JpaConfig에 설정해주자.
@SpringBootApplication // bean 읽기&생성 자동 설정. 프로젝트 최상단에 위치해야 함.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실행.
    }
}
