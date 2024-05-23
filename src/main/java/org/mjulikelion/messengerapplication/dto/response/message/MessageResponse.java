package org.mjulikelion.messengerapplication.dto.response.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mjulikelion.messengerapplication.domain.Message;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MessageResponse {

    private UUID id;
    private String title;
    private String content;


    public static MessageResponse messageResponse(Message message) {
        return new MessageResponse(message.getId(),message.getTitle(), message.getContent());
    }
}
