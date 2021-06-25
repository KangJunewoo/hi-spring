package com.medium.junewookang.springboot.web;

import com.medium.junewookang.springboot.service.posts.PostsService;
import com.medium.junewookang.springboot.web.dto.PostsListResponseDto;
import com.medium.junewookang.springboot.web.dto.PostsResponseDto;
import com.medium.junewookang.springboot.web.dto.PostsSaveRequestDto;
import com.medium.junewookang.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller 진짜 별거없음. 그냥 요청받으면 Dto 만들어서 Service 계층 호출.
 */
@RequiredArgsConstructor // Lombok - final 선언이 붙은것만.. 알지? -> 코드 변경시 생성자 변경부담 줄여줌.
@RestController // Spring - 이 클래스는 컨트롤러인데, Restful하게 동작해!
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll(){
        return postsService.findAllDesc();
    }
}
