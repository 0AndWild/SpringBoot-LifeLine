package com.mymy.blog.service;

import com.mymy.blog.domain.Blog;
import com.mymy.blog.repository.BlogRepository;
import com.mymy.blog.dto.BlogRequestDto;
import com.mymy.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor // final이라 선언 한 것만 생성자를 만들어줌!
@Service //Service 라고 명시해줌
public class BlogService {

    private final BlogRepository blogRepository;



//    public List<Blog> getBlogs(Long userId) {
//        return blogRepository.findAllByUserId(userId);
//    }

    //게시물 저장
    public Blog createBlog(BlogRequestDto requestDto, String username) {
// 요청받은 DTO 로 DB에 저장할 객체 만들기
        Blog blog = new Blog(requestDto, username);

        blogRepository.save(blog);

        return blog;
    }


    //게시물 수정
    @Transactional //이게 업데이트될 때 DB에 정말 반영이 되야된다고 말해줌
    public boolean update(Long id, BlogRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) { //boolean 타입으로 변환시켜 밑에서 true or false값을 반환
        Blog blog =  blogRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("contentsId가 존재하지 않습니다."));
        String name = blog.getUsername(); //레포에 저장된 username
        String username = userDetails.getUser().getUsername(); // 로그인한 username
        if(name.equals(username)){
            blog.update(requestDto);
            return true;
        } return false;

    }

    //게시물 삭제
    @Transactional
    public boolean delete(Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Blog blog =  blogRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("contentsId가 존재하지 않습니다."));
        String name = blog.getUsername(); //게시물에 저장된 username
        String username = userDetails.getUser().getUsername(); // 로그인한 username
        if(name.equals(username)){
            blogRepository.deleteById(id);
            return true;
        } return false;

    }


//    public Blog testMethod(Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Blog blog =  blogRepository.findById(id).orElseThrow(
//                ()->new IllegalArgumentException("contentsId가 존재하지 않습니다."));
//        String name = blog.getUsername(); //레포에 저장된 username
//        String username = userDetails.getUser().getUsername(); // 로그인한 username
//        if(name.equals(username)){
//            return blog;
//        } return null;
//
//    }
}





