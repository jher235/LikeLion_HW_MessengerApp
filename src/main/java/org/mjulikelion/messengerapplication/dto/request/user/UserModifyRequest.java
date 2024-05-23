package org.mjulikelion.messengerapplication.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserModifyRequest {

    @NotBlank(message = "수정할 이름을 입력해주세요")
    private String name;

}
