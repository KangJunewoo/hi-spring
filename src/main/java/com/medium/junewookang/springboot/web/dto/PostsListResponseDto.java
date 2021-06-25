package com.medium.junewookang.springboot.web.dto;

import com.medium.junewookang.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

// 알고보니 이 클래스의 객체가 리스트로 담겨있는거네. 이름과 다르게 실제로는 포스트 하나의 정보를 담고있음.
@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();

    }
}
