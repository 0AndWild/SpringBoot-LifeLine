package com.mymy.blog.domain;

import com.mymy.blog.dto.CommentsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter// 기본생성자를 만듭니다.
@Getter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Comments extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comments;


    @Column(nullable = false)
    private String username;


    public Comments(CommentsRequestDto requestDto, String username) {
        this.comments = requestDto.getComments();
        this.username = username;
    }

    public void update(CommentsRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }
}

