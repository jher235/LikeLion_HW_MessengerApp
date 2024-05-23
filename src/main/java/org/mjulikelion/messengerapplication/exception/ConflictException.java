package org.mjulikelion.messengerapplication.exception;

import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

public class ConflictException extends CustomException{
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
