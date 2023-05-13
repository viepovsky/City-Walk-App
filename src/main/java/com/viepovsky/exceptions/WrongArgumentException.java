package com.viepovsky.exceptions;

import lombok.Getter;

@Getter
public class WrongArgumentException extends IllegalArgumentException {
    private final String msg;

    public WrongArgumentException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
