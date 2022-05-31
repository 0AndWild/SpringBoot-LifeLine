package com.mymy.blog.repository;

import com.mymy.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    //JPA가 개발자 대신 적절한 SQL을 생성하여 DB에 전달하고, 객체를 자동으로 Mapping해준다
    //Blog는 Entity 클래스명 Long은 Entity 클래스의 pk의 자료형이다.
    List<Blog> findAllByOrderByCreatedAtDesc();
    //ByOrderByModifiedAt 수정된 날짜를 기준으로 정렬을 해줘 라는 것 // Desc는 내림차순으로 정렬을 말함(즉 최신순으로 정렬하는 것)
    //만약 다른 방식으로 정렬을 원하면 https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    //에 들어가 정렬 방식을 확인해보자!
}

//extends JpaRepository 는 미리작성된 여러 코드들(findAll, delete, findById, save 등)을 가져다 쓸거다 라는 것
