package org.mjulikelion.messengerapplication.dto.request.user;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    @NotNull(message = "이메일을 입력해주세요.")
    String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    String password;

}
