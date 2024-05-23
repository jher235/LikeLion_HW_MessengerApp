package org.mjulikelion.messengerapplication.dto.request.message;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class MessageRequest {

    @NotNull(message = "수신자는 필수 항목입니다.")
    private List<@Email(message = "올바르지 않은 이메일 형식입니다.") String> emails;

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotNull(message = "내용은 필수 항목입니다.")
    private String content;
}
