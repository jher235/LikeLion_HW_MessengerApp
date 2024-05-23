package org.mjulikelion.messengerapplication.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.mjulikelion.messengerapplication.dto.ErrorResponseDto;
import org.mjulikelion.messengerapplication.exception.CustomException;
import org.mjulikelion.messengerapplication.exception.DtoValidationException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(CustomException.class)//커스텀 오류 핸들
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException customException){
        writeLog(customException);
        HttpStatusCode httpStatusCode = resolveHttpStatus(customException);
        return new ResponseEntity<>(ErrorResponseDto.res(customException),httpStatusCode);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalError.class) //예측불가한 오류 500 핸들
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception){
        writeLog(exception);
        return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(
                HttpStatus.INTERNAL_SERVER_ERROR.value()),exception) ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidException(
            MethodArgumentNotValidException methodArgumentNotValidException){

        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();

        if(fieldError == null){
            return new ResponseEntity<>(ErrorResponseDto.res(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),methodArgumentNotValidException),HttpStatus.BAD_REQUEST);
        }

        ErrorCode validationErrorCode = ErrorCode.resolveValidErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(validationErrorCode,detail);
        writeLog(dtoValidationException);

        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException),HttpStatus.BAD_REQUEST);
    }


    private HttpStatusCode resolveHttpStatus(CustomException customException){
        return HttpStatus.resolve(Integer.parseInt(customException.getErrorCode().getCode()));
    }

    private void writeLog(CustomException customException){
        String errorCode = customException.getErrorCode().getCode();
        String message = customException.getMessage();
        String detail = customException.getDetail();
        log.error("[{}]{}: {}",errorCode, message, detail);
    }

    private void writeLog(Exception exception){
        log.error("[{}]{}: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception.getCause());
    }
}
