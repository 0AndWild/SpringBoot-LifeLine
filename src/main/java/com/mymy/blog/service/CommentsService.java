package com.mymy.blog.service;

import com.mymy.blog.domain.Blog;
import com.mymy.blog.domain.Comments;
import com.mymy.blog.dto.BlogRequestDto;
import com.mymy.blog.dto.CommentsRequestDto;
import com.mymy.blog.repository.CommentsRepository;
import com.mymy.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;



    //댓글 저장
    public Comments createComments(CommentsRequestDto requestDto, String username) {
// 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comments comments = new Comments(requestDto,username);

        commentsRepository.save(comments);

        return comments;
    }


    //댓글 수정
    @Transactional //이게 업데이트될 때 DB에 정말 반영이 되야된다고 말해줌
    public Long update(Long id, CommentsRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) { //boolean 타입으로 변환시켜 밑에서 true or false값을 반환
        Comments comments =  commentsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("contentsId가 존재하지 않습니다."));
        String name = comments.getUsername(); //레포에 저장된 username
        String username = userDetails.getUser().getUsername(); // 로그인한 username
        if(name.equals(username)){
            comments.update(requestDto);
            return 1L;
        } else {
            return 0L;
        }

    }

    //댓글 삭제
    @Transactional
    public boolean delete(Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comments comments =  commentsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("contentsId가 존재하지 않습니다."));
        String name = comments.getUsername(); //게시물에 저장된 username
        String username = userDetails.getUser().getUsername(); // 로그인한 username
        if(name.equals(username)){
            commentsRepository.deleteById(id);
            return true;
        } return false;

    }
}
