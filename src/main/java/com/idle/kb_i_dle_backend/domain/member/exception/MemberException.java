package com.idle.kb_i_dle_backend.domain.member.exception;

import com.idle.kb_i_dle_backend.config.exception.CustomException;
import com.idle.kb_i_dle_backend.global.codes.ErrorCode;

public class MemberException extends CustomException {

    public MemberException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

}
