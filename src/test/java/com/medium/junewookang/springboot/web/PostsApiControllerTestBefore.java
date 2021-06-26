package com.medium.junewookang.springboot.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.medium.junewookang.springboot.domain.posts.Posts;
import com.medium.junewookang.springboot.domain.posts.PostsRepository;
import com.medium.junewookang.springboot.web.dto.PostsSaveRequestDto;
import com.medium.junewookang.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/***
 * 기본적인 코드에선, WithMockUser 어노테이션이 MockMvc 환경에서만 동작하는 문제가 있음.
 * 이 테스트는 SpringBootTest인데 말이지.
 * 그래서 WebApplicationContext와 MockMvc 클래스의 객체를 통해
 * @SpringBootTest 에서 MockMvc를 사용하게 바꿔줄거임.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTestBefore {
    @LocalServerPort // 임의의 포트 설정
    private int port;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // 기본권한(GUEST) 말고 USER권한 부여해 모킹
    public void 포스트_등록하기() throws Exception {
        // given - 제목, 내용, 글쓴이가 들어간 dto와 url 준비
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when - post 요청을 쐈을 때, 상태코드는 200이며
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then - 리포지토리 함수로 싹 긁었을 때 들어가있는 그 값은 title과 content가 일치할 것이다.
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 포스트_수정하기() throws Exception {
        // given - 조건설정
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        ); // 일단 하나 저장하고

        Long updateId = savedPosts.getId(); // 똑같은 아이디로 새로운 객체 하나 더 만들어서
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        // when - 요청 날렸을때, 상태코드 200에
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then - 수정이 잘 반영되어야 함.
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}
