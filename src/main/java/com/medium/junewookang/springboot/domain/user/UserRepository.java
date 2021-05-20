package com.medium.junewookang.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 이메일을 통해 이미 가입한 사용자인지 판단하는 메소드
}
