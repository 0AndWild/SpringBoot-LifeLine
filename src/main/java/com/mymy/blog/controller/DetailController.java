package com.mymy.blog.controller;

import com.mymy.blog.domain.Blog;
import com.mymy.blog.repository.BlogRepository;
import com.mymy.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class DetailController {
    private final BlogRepository blogRepository;


    @GetMapping("/api/blogs/{id}")
    public String getBlogDetail (Model model, @PathVariable Long id) {
        Blog blog =  blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("아이디를 찾을 수 없습니다."));
        model.addAttribute("blog", blog);
        model.addAttribute("loginStatus",true);
        return "detail";
    }
}
