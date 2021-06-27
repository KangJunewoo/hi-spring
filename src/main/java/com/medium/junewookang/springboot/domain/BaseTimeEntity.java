package com.medium.junewookang.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // 롬복
@MappedSuperclass // jpa - 이 클래스를 상속할 경우, 필드들을 칼럼으로 갖도록 한다.
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 활성화, 즉 스프링 자체 칼럼 추가 기능을 말하는 듯 하다.
public abstract class BaseTimeEntity {
    @CreatedDate // 생성 시 자동저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 변경 시 자동저장
    private LocalDateTime modifiedDate;
}

