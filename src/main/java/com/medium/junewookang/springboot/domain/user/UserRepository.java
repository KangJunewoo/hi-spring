package com.medium.junewookang.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // 역시 기본 CRUD는 JpaRepository가 책임져주고
    Optional<User> findByEmail(String email); // 이미 가입된 사용자인지 이메일을 통해 판단하는 메소드 추가.
}
