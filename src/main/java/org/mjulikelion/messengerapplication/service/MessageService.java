package org.mjulikelion.messengerapplication.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.messengerapplication.domain.Message;
import org.mjulikelion.messengerapplication.domain.Inbox;
import org.mjulikelion.messengerapplication.domain.User;
import org.mjulikelion.messengerapplication.dto.request.message.MessageModifyRequest;
import org.mjulikelion.messengerapplication.dto.request.message.MessageReplyRequest;
import org.mjulikelion.messengerapplication.dto.request.message.MessageRequest;
import org.mjulikelion.messengerapplication.dto.response.message.MessageResponseList;
import org.mjulikelion.messengerapplication.dto.response.message.MessageDetailResponse;
import org.mjulikelion.messengerapplication.exception.ConflictException;
import org.mjulikelion.messengerapplication.exception.ForbiddenException;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.repository.MessageRepository;
import org.mjulikelion.messengerapplication.repository.InboxRepository;
import org.mjulikelion.messengerapplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final InboxRepository inboxRepository;
    private final UserRepository userRepository;

    public void send(MessageRequest messageRequest, User user){

        Message message = Message.builder()
                .sender(user)
                .title(messageRequest.getTitle())
                .content(messageRequest.getContent())
                .build();

        messageRepository.save(message);

        messageRequest.getEmails().stream().forEach(email-> {
            Inbox inbox = Inbox.builder()
                    .message(message)
                    .user(findUserByEmail(email))
                    .build();
            inboxRepository.save(inbox);
        });
    }

    public void modify(UUID messageId,MessageModifyRequest messageModifyRequest, User user){
        Message message = findMessageById(messageId);

        checkSender(user, message);

        message.modify(messageModifyRequest.getTitle(),messageModifyRequest.getContent());
        messageRepository.save(message);
    }


    public MessageDetailResponse findMessage(UUID messageId, User user){
        Message message = findMessageById(messageId);

        checkAuth(user,message);

        if(message.getAncestorMessageId()==null){
            return new MessageDetailResponse(message);
        }
        return new MessageDetailResponse(message, findMessageById(message.getAncestorMessageId()));
    }

    public void reply(UUID messageId, MessageReplyRequest messageReplyRequest , User user){
        Message message = findMessageById(messageId);
        checkReceiver(user, message);
        Message replyMessage = Message.builder()
                .ancestorMessageId(messageId)
                .title(messageReplyRequest.getTitle())
                .content(messageReplyRequest.getContent())
                .sender(user)
                .build();

        messageRepository.save(replyMessage);

        Inbox inbox = Inbox.builder()
                .user(message.getSender())
                .message(replyMessage)
                .build();

        inboxRepository.save(inbox);
    }

    public MessageResponseList findReceivedMessages(User user){
        List<Inbox> inboxes = findInboxByUser(user);
        List<Message> messages = inboxes.stream().map(i ->i.getMessage()).collect(Collectors.toList());
        return new MessageResponseList(messages);
    }

    public MessageResponseList findSentMessages(User user){
        List<Message> messages = findMessagesByUser(user);
        return new MessageResponseList(messages);
    }

    public void delete(UUID messageId, User user){
        Message message = findMessageById(messageId);
        checkSender(user, message);
        if(message.isRead()){
            throw new ConflictException(ErrorCode.MESSAGE_VIEWED_CONFLICT);
        }
        messageRepository.delete(message);
    }


    private User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private Message findMessageById(UUID id){
        return messageRepository.findById(id).orElseThrow(()-> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
    }

    private List<Inbox> findInboxByUser(User user){
        return inboxRepository.findInboxesByUser(user).orElseThrow(()-> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
    }

    private List<Message> findMessagesByUser(User user){
        return messageRepository.findAllBySender(user).orElseThrow(()-> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
    }

    private void checkSender(User user, Message message){
        if (!message.getSender().getId().equals(user.getId())){
            throw new ForbiddenException(ErrorCode.MESSAGE_FORBIDDEN);
        }
    }

    private void checkReceiver(User user, Message message){

        if(!inboxRepository.existsByUserAndMessage(user,message)){
            throw new ForbiddenException(ErrorCode.MESSAGE_FORBIDDEN);
        }
        message.read();
    }

    private void checkAuth(User user, Message message){

        if(message.getSender().getId().equals(user.getId())){
            return;
        }
        checkReceiver(user,message);
    }


}
