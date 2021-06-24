package com.medium.junewookang.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * MyBatis같은 SQL Mapper 에선 Dao라고 불림.. 하지만 내가 배운 JPA 세계에선 Repository가 맞지!
 * JpaRepository<엔티티클래스, PK타입>을 상속해주면 자동빵으로 CRUD 메소드 생성.
 * 미쳤다 @Repository 추가할 필요도 없네.. 편하네..
 *
 */
public interface PostsRepository extends JpaRepository<Posts, Long>{
    @Query("SELECT p FROM Posts p ORDER  BY p.id DESC")
    List<Posts> findAllDesc();
}