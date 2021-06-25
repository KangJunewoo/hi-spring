package com.medium.junewookang.springboot.service.posts;

import com.medium.junewookang.springboot.domain.posts.Posts;
import com.medium.junewookang.springboot.domain.posts.PostsRepository;
import com.medium.junewookang.springboot.web.dto.PostsListResponseDto;
import com.medium.junewookang.springboot.web.dto.PostsResponseDto;
import com.medium.junewookang.springboot.web.dto.PostsSaveRequestDto;
import com.medium.junewookang.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // Lombok - 알지?
@Service // Spring - 난 서비스 계층이야!
public class PostsService {
    /**
     * @Autowired를 선언해주지 않았음에도 postsRepository를 주입받음.
     * 그 이유는 생성자(@RequiredArgsConstructor)를 통해 이미 주입이 받아진 상태이기 때문.
     * 또한 SQL 입력 없이 postsRepository로 접근하는 다양한 DB접근 함수들이 계속 쓰이는데
     * 이는 JPA의 영속성 컨텍스트(엔티티를 영구 저장하는 환경) 때문이라고 함.
     * 그리고 서비스가 컨트롤러에서 직접 사용하는 핵심적인 기능이므로, 트랜잭션으로 묶어야 하기 때문에 @Transactional을 사용하나 봄.
     *
     */
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent()); // 도메인 메소드 호출

        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly=true) // 조회만 할때 readOnly true로 해주면 속도개선 가능.
    public List<PostsListResponseDto> findAllDesc(){
        /***
         * 여기서 문법 하나 짚고 넘어가자.
         * 스트림은 배열이나 컬렉션으로 원하는 값을 얻을 때, for문 방지를 위해 자바8에서 나온 개념이다.
         * 그냥 값들 모음인데 다양한 체이닝 가능 내장함수들이 제공된다고 생각하면 됨.
         *
         * stream : 리스트를 스트림 형태로 만들고
         * map : 각각의 원소에 다음을 씌울건데
         * PostsListResponseDto::new : 각각을 PostsListResponseDto로 만든 후
         * (posts -> new PostsListResponseDto(posts)와 같음)
         * collect : 반환을 할건데
         * Collectors.toList() : 리스트형태로 하겠다.
         */
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }


}
