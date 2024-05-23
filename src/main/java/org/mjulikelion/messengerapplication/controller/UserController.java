package org.mjulikelion.messengerapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedUser;
import org.mjulikelion.messengerapplication.domain.User;
import org.mjulikelion.messengerapplication.dto.ResponseDto;
import org.mjulikelion.messengerapplication.dto.request.user.UserModifyRequest;
import org.mjulikelion.messengerapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController{
    private final UserService userService;

    @PutMapping
    public ResponseEntity<ResponseDto<Void>> modify(
            @RequestBody @Valid UserModifyRequest userModifyRequest, @AuthenticatedUser User user){

        userService.modify(userModifyRequest, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"유저 정보 수정 완료"),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> withdraw(@AuthenticatedUser User user){
        userService.delete(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"유저 탈퇴 완료"),HttpStatus.OK);
    }

}
