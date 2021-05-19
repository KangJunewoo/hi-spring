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

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

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
        // 람다식 (PostsListResponseDto::new)는 (posts -> new PostsListResponseDto(posts)) 와 같음.
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }


}
