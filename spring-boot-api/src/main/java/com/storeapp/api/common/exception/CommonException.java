package com.storeapp.api.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * CommonException
 *
 * @author khal
 * @since 2020/09/29
 */
@Getter
@Setter
public class CommonException extends RuntimeException {

    private String code;
    private String message;
    private Object[] args;

    public CommonException() {
        this(null, null, null);
    }
    
    public CommonException(final String messageCode) {
        this(messageCode, messageCode, null);
    }

    public CommonException(final String messageCode, final Object[] args) {
        this(messageCode, messageCode, args);
    }

    public CommonException(final String errorCode, final String messageCode) {
        this(errorCode, messageCode, null);

    }

    public CommonException(final String code, final String message, final Object[] args) {
        this.code = code;
        this.message = message;
        this.args = args;
    }

}
