package com.example.prac.error;

import com.example.prac.error.ex.ExceptionApi400;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Slf4j
@Aspect
@Component
public class GlobalValidationHandler {

    // TODO : TEST
    @Pointcut("execution(* com.example.prac.user.UserController.testAop(..))")
    public void testMethod() {
    }

    // TODO : TEST
    @Before("testMethod()")
    public void logTestMethod(JoinPoint joinPoint) {
        log.info("AOP is working on test method: {}", joinPoint.getSignature().getName());
    }

    @Pointcut ("execution(* com.example.prac.user.UserController.userSave(..)) || " +
            "execution(* com.example.prac.user.UserController.userSelect(..)) || " +
            "execution(* com.example.prac.user.UserController.userUpdate(..))")
    public void userApiMethods() {
        System.out.println("=================메서드 aop 실행==================");
    }

    @Before("userApiMethods()")
    public void clientAgentCheck(JoinPoint jp) {
        // 현재 요청 정보를 가져오기 위해 RequestContextHolder 사용
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // 클라이언트 User-Agent 로그 출력
            String userAgent = request.getHeader("User-Agent");
            log.info("Client User-Agent: {}", userAgent);

            // 메서드 이름 및 파라미터 로그 출력
            log.info("Executing method: {} with arguments: {}", jp.getSignature().getName(), jp.getArgs());
        }
    }
}
