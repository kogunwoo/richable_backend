package com.idle.kb_i_dle_backend.domain.member.handler;

import com.idle.kb_i_dle_backend.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseDTO> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        ResponseDTO response = new ResponseDTO(false, "인증 토큰이 필요합니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
