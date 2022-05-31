package com.mymy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BlogRequestDto { // RequestDto는 필요한 정보를 몰고다니는 녀석
    //수정요청이 왔으면 일단 두가지 정보가 필요 //유져 이름과, 내용(contents)

    private String contents;
    private String title;
//    private String userName;
//    private String comments;
}
