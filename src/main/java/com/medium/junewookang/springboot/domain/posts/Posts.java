package com.medium.junewookang.springboot.domain.posts;

import com.medium.junewookang.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 엔티티 클래스에선 절대!!! Setter를 만들지 않는다!!!
 * 웬만하면 생성자를 통해 값 확정짓고
 * 값을 변경하고 싶으면 public 메소드 활용해야함.
 */
@Getter // 롬복 - 알지?
@NoArgsConstructor // 롬복 - 인자 없는 생성자 생성
@Entity // JPA - 이 클래스는 테이블이다.
public class Posts extends BaseTimeEntity { // TODO : BaseTimeEntity?

    @Id // JPA - 이 필드는 PK이다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA - PK의 생성 규칙을 정의할건데, auto_increment로 설정하겠다.
    private Long id;

    @Column(length=500, nullable=false) // JPA - 이 필드는 PK 아닌 칼럼이고, 옵션을 지정하겠다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    private String author; // @Column 안붙이면 기본 VARCHAR(255) 칼럼으로 생성됨.

    @Builder // 롬복 - .으로 계속 이어나가는 빌더패턴클래스 자동생성. title, content, author에 적용 가능. (와 이건 좀 편하다)
    public Posts (String title, String content, String author){
        this.title=title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
