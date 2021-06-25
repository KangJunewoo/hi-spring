package com.medium.junewookang.springboot.domain.posts;

import org.assertj.core.api.LocalDateTimeAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * PostsRepository에서 JPA를 사용했기 때문에, 선언을 아무것도 안했지만
 * deleteAll, save, findAll 등의 메소드가 자동으로 지원되는 것을 확인할 수 있음.
 */
@ExtendWith(SpringExtension.class) // HelloControllerTest에서 봤었지? junit을 스프링용으로 확장해주고
@SpringBootTest // h2 db를 실행해줌.
public class PostsRepositoryTest {

    @Autowired // 키야 자동주입
    PostsRepository postsRepository;

    @AfterEach // 각각의 테스트가 끝난 후 삭제 수행
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        String title = "테스트게시글";
        String content = "테스트본문";

        postsRepository.save(Posts.builder() // id값이 있다면 update, 없다면 insert가 수행됨.
            .title(title)
            .content(content)
            .author("test@email.com")
            .build());

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0); // 첫번째거 가져오기
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0,0,0); // 머나먼 옛날
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build()); // 최신

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now); // 머나먼 옛날 < 최신
        assertThat(posts.getModifiedDate()).isAfter(now);
    }




}
