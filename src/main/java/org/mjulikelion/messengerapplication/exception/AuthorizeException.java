package org.mjulikelion.messengerapplication.exception;

import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

public class AuthorizeException extends CustomException{
    public AuthorizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizeException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
