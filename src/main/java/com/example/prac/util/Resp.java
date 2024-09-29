package com.example.prac.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Resp<T> {
    private Integer status;
    private String reason;
    private T body;

    public static <B> Resp<?> ok(B body){
        return new Resp<>(200, "성공", body);
    }

    public static <B> Resp<?> ok(B body, String reason){ // 오버로딩
        return new Resp<>(200, reason , body);
    }

    public static Resp<?> fail(Integer status, String reason){
        return new Resp<>(status, reason, null);
    }
}
