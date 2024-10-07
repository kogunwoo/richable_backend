package com.idle.kb_i_dle_backend.domain.goal.exception;

import com.idle.kb_i_dle_backend.config.exception.CustomException;
import com.idle.kb_i_dle_backend.global.codes.ErrorCode;

public class GoalException extends CustomException {
    public GoalException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
