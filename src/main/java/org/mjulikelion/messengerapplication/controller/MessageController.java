package org.mjulikelion.messengerapplication.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedUser;
import org.mjulikelion.messengerapplication.domain.User;
import org.mjulikelion.messengerapplication.dto.ResponseDto;
import org.mjulikelion.messengerapplication.dto.request.message.MessageModifyRequest;
import org.mjulikelion.messengerapplication.dto.request.message.MessageReplyRequest;
import org.mjulikelion.messengerapplication.dto.request.message.MessageRequest;
import org.mjulikelion.messengerapplication.dto.response.message.MessageResponse;
import org.mjulikelion.messengerapplication.dto.response.message.MessageResponseList;
import org.mjulikelion.messengerapplication.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> send(
            @RequestBody @Valid MessageRequest messageRequest, @AuthenticatedUser User user){
        messageService.send(messageRequest, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메세지 전송 성공"),HttpStatus.OK);
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<ResponseDto<Void>> modify(
            @PathVariable UUID messageId, @Valid @RequestBody MessageModifyRequest messageModifyRequest,
            @AuthenticatedUser User user){
        messageService.modify(messageId, messageModifyRequest, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메세지 수정 성공"),HttpStatus.OK);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<ResponseDto<MessageResponse>> findMessage(
            @PathVariable UUID messageId, @AuthenticatedUser User user){
        MessageResponse messageResponse = messageService.findMessage(messageId, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메세지 조회 성공",messageResponse),HttpStatus.OK);
    }

    @PostMapping("/{messageId}")
    public ResponseEntity<ResponseDto<Void>> reply(
            @PathVariable UUID messageId, @Valid @RequestBody MessageReplyRequest messageReplyRequest, @AuthenticatedUser User user){
        messageService.reply(messageId, messageReplyRequest, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메세지 회신 성공"),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MessageResponseList>> watchReceiveList(@AuthenticatedUser User user){
        MessageResponseList messageResponseList = messageService.findReceivedMessages(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"받은 메세지 목록", messageResponseList),HttpStatus.OK);
    }

    @GetMapping("/sent")//유저 컨트롤러에서 처리하도록 변경하는게 나을까요?
    public ResponseEntity<ResponseDto<MessageResponseList>> watchSendList(@AuthenticatedUser User user){
        MessageResponseList messageResponseList = messageService.findSentMessages(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"보낸 메세지 목록", messageResponseList),HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ResponseDto<Void>> delete(
            @PathVariable UUID messageId, @AuthenticatedUser User user){
        messageService.delete(messageId,user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메세지 삭제 성공"),HttpStatus.OK);
    }


}
