package com.example.prac.error.ex;

// 인증관련
public class ExceptionApi401 extends RuntimeException {
    public ExceptionApi401(String message) {
        super(message);
    }
}
