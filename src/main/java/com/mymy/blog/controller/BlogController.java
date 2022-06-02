package com.mymy.blog.controller;

import com.mymy.blog.domain.Blog;
import com.mymy.blog.repository.BlogRepository;
import com.mymy.blog.dto.BlogRequestDto;
import com.mymy.blog.repository.UserRepository;
import com.mymy.blog.security.UserDetailsImpl;
import com.mymy.blog.service.BlogService;
import com.mymy.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Component
@RequiredArgsConstructor
@RestController
public class BlogController {

    private final BlogRepository blogRepository;
    private final BlogService blogService;

    private final UserRepository userRepository;

    private final UserService userService;



    @PostMapping("/api/blogs")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //클라가 데이터를 보낼텐데 그 데이터를 requesrtDto에 정확히 넣어줘라고 하기 위해선
        //@RequestBody 앞에 붙여서 요청이 들어올때 Body라는 녀석이 들어있는거를 requesrtDto 넣어서 보내줘라고 해야함
        String username = userDetails.getUser().getUsername();

        Blog blog = blogService.createBlog(requestDto, username);

// 응답 보내기
        return blog;
    }


    //전체 게시글 조회
    @GetMapping("/api/blogs")
    public List<Blog> getBlog() {
        return blogRepository.findAllByOrderByCreatedAtDesc();
    }




    //게시물 삭제
    @DeleteMapping("/api/blogs/{id}")
    public Long deleteBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(blogService.delete(id,userDetails)){
            return 1L;
        } else {
            return 0L;
        }

    }

//        @PathVariable은 경로안에있는(api의 중괄호{}) 정보를 넣어준다는 의미
//         (이게 없으면 어떤 아이디인지 찾지 못해 밑에 return id; 에서 에러가남!

    //게시물 수정
    @PutMapping ("/api/blogs/{id}")
    public Long updateBlog(@PathVariable Long id,@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(blogService.update(id,requestDto,userDetails) == 1L){
            return 1L;
        } else {
            return 0L;
        }


//     if(blogService.update(id, requestDto)){//blogService.update(id, requestDto) == true로 이해하면 될 것 같다. // ctrl + update 누르면 사용된 위치로 가게됨
//         return 1L; //service class에서 true를 반환하면 1로 return // L은 Long 타입으로 선언하였기 떄문에 붙여줘야 에러가 안남!
//     } else {
//         return 0L; //service class에서 false를 반환하면 0로 return
//     }

    }
}