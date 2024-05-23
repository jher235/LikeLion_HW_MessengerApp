package org.mjulikelion.messengerapplication.dto.response.message;

import lombok.Getter;
import org.mjulikelion.messengerapplication.domain.Inbox;
import org.mjulikelion.messengerapplication.domain.Message;

import java.util.List;
import java.util.stream.Collectors;



@Getter
public class MessageResponseList {
    private int count;
    private List<MessageResponse> messageResponses;

    public MessageResponseList(List<Message> messages) {
        this.count = messages.size();
        this.messageResponses = messages.stream().map(m->
                MessageResponse.messageResponse(m)
        ).collect(Collectors.toList());
    }
}
