package com.mymy.blog.controller;

import com.mymy.blog.domain.Blog;
import com.mymy.blog.domain.Comments;
import com.mymy.blog.dto.BlogRequestDto;
import com.mymy.blog.dto.CommentsRequestDto;
import com.mymy.blog.repository.BlogRepository;
import com.mymy.blog.repository.CommentsRepository;
import com.mymy.blog.repository.UserRepository;
import com.mymy.blog.security.UserDetailsImpl;
import com.mymy.blog.service.BlogService;
import com.mymy.blog.service.CommentsService;
import com.mymy.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Component
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentsRepository commentsRepository;
    private final CommentsService commentsService;



    //댓글작성
    @PostMapping("/api/comments")
    public Comments createComment(@RequestBody CommentsRequestDto requestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //클라가 데이터를 보낼텐데 그 데이터를 requesrtDto에 정확히 넣어줘라고 하기 위해선
        //@RequestBody 앞에 붙여서 요청이 들어올때 Body라는 녀석이 들어있는거를 requesrtDto 넣어서 보내줘라고 해야함
        String username = userDetails.getUser().getUsername();

        Comments comments = commentsService.createComments(requestDto, username);

// 응답 보내기
        return comments;
    }


    //전체 댓글조회
    @GetMapping("/api/comments")
    public List<Comments> getComments() {
        return commentsRepository.findAllByOrderByCreatedAtDesc();
    }




    //댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public Long deleteComments(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(commentsService.delete(id,userDetails)){
            return 1L;
        } else {
            return 0L;
        }

    }

//        @PathVariable은 경로안에있는(api의 중괄호{}) 정보를 넣어준다는 의미
//         (이게 없으면 어떤 아이디인지 찾지 못해 밑에 return id; 에서 에러가남!

    //댓글 수정
    @PutMapping ("/api/comments/{id}")
    public Long updateComments(@PathVariable Long id,@RequestBody CommentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(commentsService.update(id,requestDto,userDetails) == 1L){
            return 1L;
        } else {
            return 0L;
        }

    }

}
