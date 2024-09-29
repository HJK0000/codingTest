package com.example.prac.error;


import com.example.prac.error.ex.*;
import com.example.prac.util.Resp;
import com.example.prac.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalApiExceptionHandler {
    
    // 유효성 검사 실패 (잘못된 클라이언트의 요청)
    @ExceptionHandler({ExceptionApi400.class})
    public ResponseEntity<?> ex400(Exception e) {

        return new ResponseEntity<>(Resp.fail(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 추가로 유효성 검사 실패 시 발생하는 MethodArgumentNotValidException도 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        String reason = "유효하지 않은 요청입니다";
        return new ResponseEntity<>(Resp.fail(400, reason), HttpStatus.BAD_REQUEST);
    }

    // 인증 실패 (클라이언트가 인증없이 요청했거나, 인증을 하거나 실패했거나)
    @ExceptionHandler(ExceptionApi401.class)
    public ResponseEntity<?> ex401(Exception e) {

        return new ResponseEntity<>(Resp.fail(401, e.getMessage()), HttpStatus.UNAUTHORIZED); // 인증안됨
    }

    // 권한 실패 (인증은 되어 있는데, 삭제하려는 게시글이 내가 적은게 아니다)
    @ExceptionHandler(ExceptionApi403.class)
    public ResponseEntity<?> ex403(Exception e) {

        return new ResponseEntity<>(Resp.fail(403, e.getMessage()), HttpStatus.FORBIDDEN); // 권한없음
    }

    // 서버에서 리소스(자원) 찾을 수 없을때
    @ExceptionHandler(ExceptionApi404.class)
    public ResponseEntity<?> ex404(Exception e) {
        return new ResponseEntity<>(Resp.fail(404, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    // 존재하지 않는 API 요청 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return new ResponseEntity<>(Resp.fail(404, "요청하신 API를 찾을 수 없습니다!!!"), HttpStatus.NOT_FOUND);
    }

    // 서버에서 심각한 오류가 발생했을때 (알고 있을 때)
    @ExceptionHandler(ExceptionApi500.class)
    public ResponseEntity<?> ex500(Exception e) {
        return new ResponseEntity<>(Resp.fail(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 서버에서 심각한 오류가 발생했을때 (모를 때)
    @ExceptionHandler(Exception.class)
    public String ex(Exception e) {
        return Script.back(e.getMessage());
    }

}







