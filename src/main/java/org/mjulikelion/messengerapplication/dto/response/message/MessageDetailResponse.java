package org.mjulikelion.messengerapplication.dto.response.message;


import lombok.Getter;
import org.mjulikelion.messengerapplication.domain.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Getter
public class MessageDetailResponse extends MessageResponse{


    private MessageResponse ancestorMessageResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean viewed;

    public MessageDetailResponse(Message message) {
        super(message.getId(),message.getTitle(), message.getContent());
        this.ancestorMessageResponse = null;
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
        this.viewed = message.getViewed();
    }

    public MessageDetailResponse(Message message, Message ancestorMessage) {
        super(message.getId(), message.getTitle(), message.getContent());
        this.ancestorMessageResponse = MessageResponse.messageResponse(ancestorMessage);
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
        this.viewed = message.getViewed();

    }



}
