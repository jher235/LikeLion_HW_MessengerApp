package org.mjulikelion.messengerapplication.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_NULL("400", "필수값이 누락되었습니다."),
    NOT_BLANK("400", "필수값이 공백입니다."),
    SIZE("400", "길이가 유효하지 않습니다"),
    EMAIL("400", "잘못된 이메일 형식입니다"),

    USER_UNAUTHORIZED("401", "로그인에 실패했습니다."),
    TOKEN_INVALID("401", "유효하지 않은 토큰입니다"),
    TOKEN_NOT_FOUND("401", "토큰을 찾을 수 없습니다."),

    MESSAGE_FORBIDDEN("403", "해당 메세지에 대한 권한이 없습니다"),

    USER_NOT_FOUND("404","유저 정보를 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND("404","메시지 정보를 찾을 수 없습니다."),

    USER_CONFLICT("409", "이미 존재하는 유저입니다."),
    MESSAGE_CONFLICT("409", "이미 존재하는 메세지입니다."),
    MESSAGE_VIEWED_CONFLICT("409", "이미 조회한 메세지로 수정 및 삭제가 불가합니다.");


    private final String code;
    private final String message;

    public static ErrorCode resolveValidErrorCode(String code){
        return switch (code){
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Size" -> SIZE;
            case "Email" -> EMAIL;
            default -> throw new IllegalArgumentException("UnExpected Exception: "+ code);
        };
    }



}
