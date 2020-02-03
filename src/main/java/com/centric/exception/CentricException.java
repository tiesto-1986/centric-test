package com.centric.exception;

import org.springframework.http.HttpStatus;

public class CentricException extends RuntimeException {

	public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    private HttpStatus httpStatus = DEFAULT_HTTP_STATUS;

    public CentricException() {
        super();
    }

    public CentricException(Throwable e) {
        super(e);
    }

    public CentricException(String message, Throwable e) {
        super(message, e);
    }

    public CentricException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CentricException(HttpStatus httpStatus) {
        this.setHttpStatus(httpStatus);
    }

    public CentricException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public CentricException withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
