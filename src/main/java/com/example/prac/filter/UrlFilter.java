package com.example.prac.filter;


import com.example.prac.util.Resp;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class UrlFilter implements Filter {

    // 정규식을 통해 허용된 특수문자를 제외한 나머지를 필터링
    private static final Pattern DISALLOWED_SPECIAL_CHARACTERS = Pattern.compile("[^a-zA-Z0-9\\-_/?:&=]");


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 요청 URL 가져오기
        String requestUrl = req.getRequestURI();
        
        
        // h2 console 로 요청이 들어오면 필터링 시키지말고 통과시키기
        if (requestUrl.startsWith("/h2-console")) {
            chain.doFilter(request, response);
            return;  // 필터링을 건너뛰고 반환
        }

        // 특수문자 필터링
        if (DISALLOWED_SPECIAL_CHARACTERS.matcher(requestUrl).find()) {
            //resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL에 사용불가한 특수기호가 포함되어있습니다.");

            // HTTP 상태 코드를 400으로 설정
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            
            // 응답 본문
            resp.setContentType("application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            Resp fail = Resp.fail(400, "URL에 사용불가한 특수기호가 포함되어있습니다.");
            String responseBody = new ObjectMapper().writeValueAsString(fail);
            out.println(responseBody);
            out.flush();
            return;
        }

        // 필터 체인 진행 (다음 필터 또는 컨트롤러로 요청 전달)
        // 이거 없으면 컨트롤러로 안감
        chain.doFilter(request, response);

    }

}
