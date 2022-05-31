package com.mymy.blog.service;


import com.mymy.blog.domain.UserRoleEnum;
import com.mymy.blog.dto.SignupRequestDto;
import com.mymy.blog.domain.Users;
import com.mymy.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired //WebsecurityConfig의 @Bean으로 설정된 BCrypt의 PasswordEncoder 를 DI함
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequestDto requestDto) {
// 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

// 패스워드 암호화
        // 사용자가 입력한 비밀번호 requestDto.getPassword() 가 passwordEncoder.encode 에 의해 암호화됨.
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

// 사용자 ROLE 확인 // 이거를 admin token을 날리고 일반 member와 User로 구분하여 user에게만 작성 및 댓글 수정 삭제를 가능하게 해야 함
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN; //입력한 관리자 토큰과 일치할 경우 관리자로 회원가입
        }

        Users user = new Users(username, password, email, role);
        userRepository.save(user); //암호화된 비밀번호로 다시 정의되고 밑에서 .save에 의해 DB에 저장
    }
}