package org.mjulikelion.messengerapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.detail = null;
    }

    public CustomException(ErrorCode errorCode, String detail){
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
