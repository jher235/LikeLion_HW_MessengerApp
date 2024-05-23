package org.mjulikelion.messengerapplication.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterRequest {

    @Email
    @NotNull(message = "이메일은 필수 항목입니다.")
    String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 5, max = 20, message = "비밀번호는 5자 이상, 20자 이하로 입력해주세요.")
    String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Size(max = 20, message = "이름은 20자 이하로 입력해주세요.")
    String name;
}
