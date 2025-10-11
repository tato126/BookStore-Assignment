package org.bookstore.bookstore.user.controller.login;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.user.dto.request.UserRequest;
import org.bookstore.bookstore.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link org.apache.catalina.User} 컨트롤러 클래스.
 *
 * @author chan
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserLoginController {

    private final UserService userService;

    // 회원가입 페이지 표시
    @GetMapping("/signup")
    public String signupForm() {
        return "user/signup";
    }

    // 회원가입 처리
    @PostMapping("/signUp")
    public String createUser(UserRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return "redirect:/user/login"; // 회원가입 성공 후 로그인 페이지로 리다이렉트
    }

    // 로그인 페이지 처리
    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginUser(UserRequest userLoginRequest) {

        userService.login(userLoginRequest);

        return "redirect:/products/list";
    }
}
