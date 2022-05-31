package com.mymy.blog.domain;

import com.mymy.blog.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter// 기본생성자를 만듭니다.
@Getter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Blog extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private  String title;

//    @Column(nullable = false)
//    private String comments;

    @Column(nullable = false)
    private String username;




    public Blog(BlogRequestDto requestDto, String username) {

        this.username = username;
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
//        this.comments = requestDto.getComments();


    }

    public void update(BlogRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
    }

//    public void update(BlogRequestDto requestDto, Long userId) {
//        this.userId = requestDto.getUserId();
//        this.contents = requestDto.getContents();
//
//
//    }

}
