package com.chao.summer.exception;

public class SummerException extends RuntimeException {
    public SummerException() {
        super("Summer Exception");
    }

    public SummerException(String message) {
        super(message);
    }
}
