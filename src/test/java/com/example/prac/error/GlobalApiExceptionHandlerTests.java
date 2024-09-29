package com.example.prac.error;

import com.example.prac.user.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GlobalApiExceptionHandlerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 잘못된_요청_400_테스트() {
        // Given: 유효하지 않은 요청 데이터 (예: 빈 이름 리스트)
        UserRequest.SaveMultipleDTO invalidRequest = new UserRequest.SaveMultipleDTO();
        invalidRequest.setName(Collections.emptyList()); // 빈 이름 리스트로 잘못된 요청

        HttpEntity<UserRequest.SaveMultipleDTO> requestEntity = new HttpEntity<>(invalidRequest);

        // When: /users API로 잘못된 요청을 보냄
        ResponseEntity<String> response = restTemplate.exchange("/users", HttpMethod.POST, requestEntity, String.class);

        // Then: HTTP 상태 코드가 400인지, 응답 메시지가 맞는지 확인
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST); // 400 응답 확인
        assertThat(response.getBody()).contains("reason"); // 응답에 "reason" 필드가 있는지 확인
        assertThat(response.getBody()).contains("유효하지 않은 요청입니다"); // 실제 사유가 포함된 메시지 검증
    }

    @Test
    public void 존재하지_않는_API_호출_테스트() {
        // Given: 잘못된 API 경로
        String invalidUrl = "/invalid-api";

        // When: 잘못된 경로로 요청
        ResponseEntity<String> response = restTemplate.exchange(invalidUrl, HttpMethod.GET, null, String.class);

        // Then: HTTP 상태 코드가 404인지, 응답 메시지가 맞는지 확인
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("요청하신 API를 찾을 수 없습니다!!!");
    }
}

