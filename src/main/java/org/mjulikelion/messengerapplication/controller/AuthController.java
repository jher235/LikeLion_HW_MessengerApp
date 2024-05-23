package org.mjulikelion.messengerapplication.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedUser;
import org.mjulikelion.messengerapplication.authentication.JwtEncoder;
import org.mjulikelion.messengerapplication.authentication.JwtTokenProvider;
import org.mjulikelion.messengerapplication.domain.User;
import org.mjulikelion.messengerapplication.dto.ResponseDto;
import org.mjulikelion.messengerapplication.dto.request.user.LoginRequest;
import org.mjulikelion.messengerapplication.dto.request.user.UserRegisterRequest;
import org.mjulikelion.messengerapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        userService.register(userRegisterRequest);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED,"회원가입 성공"),HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        UUID userId = userService.login(loginRequest);
        String accessToken = jwtTokenProvider.createToken(String.valueOf(userId));

        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofMinutes(60*30))
                .path("/")
                .httpOnly(true)//브라우저에서 쿠키에 접근 못하도록
                .build();

        log.info(cookie.toString());
        httpServletResponse.addHeader("Set-Cookie",cookie.toString());//헤더의 Set-Cookie에 쿠키 저장
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"로그인 성공"),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(@AuthenticatedUser User user, HttpServletResponse httpServletResponse){

        ResponseCookie cookie = ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .build();

        httpServletResponse.addHeader("Set-Cookie",cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"로그아웃 성공"),HttpStatus.OK);
    }

    //@AuthenticatedUser테스트
    @GetMapping("/test")
    public ResponseEntity<ResponseDto<String>> test(@AuthenticatedUser User user){
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"커스텀 어노테이션 테스트",user.getEmail()),HttpStatus.OK);
    }


}
