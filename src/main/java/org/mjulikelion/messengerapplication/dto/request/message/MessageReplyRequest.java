package org.mjulikelion.messengerapplication.dto.request.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MessageReplyRequest {

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotNull(message = "내용은 필수 항목입니다.")
    private String content;

}
