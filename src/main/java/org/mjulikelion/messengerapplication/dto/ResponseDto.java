package org.mjulikelion.messengerapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private final String statusCode;
    private final String message;
    private final T data;

    //반환할 데이터가 Void일 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message){
        return new ResponseDto<>(String.valueOf(statusCode), message, null);
    }

    //반환해줄 데이터가 존재할 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message, T data){
        return new ResponseDto<>(String.valueOf(statusCode), message, data);
    }

}
