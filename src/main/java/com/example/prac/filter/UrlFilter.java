package com.example.prac.filter;


import com.example.prac.util.Resp;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        // 특수문자 필터링
        if (DISALLOWED_SPECIAL_CHARACTERS.matcher(requestUrl).find()) {
            //resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL에 사용불가한 특수기호가 포함되어있습니다.");
            resp.setContentType("application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            Resp fail = Resp.fail(400, "URL에 사용불가한 특수기호가 포함되어있습니다.");
            String responseBody = new ObjectMapper().writeValueAsString(fail);
            out.println(responseBody);
            out.flush();
            return;
        }

    }

}
