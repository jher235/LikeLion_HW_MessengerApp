package org.mjulikelion.messengerapplication.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mjulikelion.messengerapplication.exception.CustomException;

@AllArgsConstructor
@Getter
@JsonPropertyOrder("{errorCode, message, detail}")//json으로 반환 시 순서 설정
public class ErrorResponseDto {

    private final String errorCode;
    private final String message;
    private final String detail;

    public static ErrorResponseDto res(final CustomException customException){
        return new ErrorResponseDto(
                customException.getErrorCode().getCode(), customException.getErrorCode().getMessage(), customException.getDetail());
    }

    public static ErrorResponseDto res(final String errorCode, final Exception exception){
        return new ErrorResponseDto(errorCode, exception.getMessage(),null);
    }
}
