package com.mymy.blog.controller;

import com.mymy.blog.dto.SignupRequestDto;
import com.mymy.blog.security.UserDetailsImpl;
import com.mymy.blog.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") //로그인한 페이지
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null) {
            model.addAttribute("username",  userDetails.getUsername()); //key:value
            model.addAttribute("loginStatus", true);
            return "index";
        } else {
            model.addAttribute("username");
//            model.addAttribute("loginStatus", true);
            return "index";
        }
    }

}