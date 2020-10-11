package com.storeapp.api.common.exception;

/**
 * ServerErrorException
 *
 * @author khal
 * @since 2020/09/29
 */
public class ServerErrorException extends CommonException {

    public ServerErrorException() {
        this(null, null, null);
    }

    public ServerErrorException(final String messageCode) {
        this(messageCode, messageCode, null);
    }

    public ServerErrorException(final String messageCode, final Object[] args) {
        this(messageCode, messageCode, args);
    }

    public ServerErrorException(final String errorCode, final String messageCode) {
        this(errorCode, messageCode, null);
    }

    public ServerErrorException(final String code, final String message, final Object[] args) {
        super(code, message, args);
    }

}
