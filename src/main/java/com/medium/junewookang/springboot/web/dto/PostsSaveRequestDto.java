package com.medium.junewookang.springboot.web.dto;

import com.medium.junewookang.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Builder를 사용하기 위해선 NoArgsConstructor가 있어야하나보다. 생각해보니 그러네 ㅇㅇ.
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
    }
}
