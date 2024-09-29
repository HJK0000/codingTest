package com.example.prac.user;

import com.example.prac.PracApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = PracApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/users";
    }

    @Test
    @Sql("/db/cleanup.sql") // 테스트 실행 전 데이터베이스 정리
    public void 여러_사용자_등록_성공_테스트() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        UserRequest.SaveMultipleDTO request = new UserRequest.SaveMultipleDTO();
        request.setName(List.of("qw", "as"));

        HttpEntity<UserRequest.SaveMultipleDTO> entity = new HttpEntity<>(request, headers);

        // When
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                baseUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 상태 코드 확인
        Map<String, Object> responseBody = response.getBody();
        List<Map<String, Integer>> bodyList = (List<Map<String, Integer>>) responseBody.get("body");

        // 응답 body 안에 있는 id 값 검증
        assertThat(bodyList).hasSize(2); // 두 명의 유저가 등록되었는지 확인
        assertThat(bodyList.get(0).get("id")).isNotNull();
        assertThat(bodyList.get(1).get("id")).isNotNull();
    }

    @Test
    @Sql("/db/cleanup.sql")
    public void 중복된_사용자_등록_실패_테스트() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        UserRequest.SaveMultipleDTO request = new UserRequest.SaveMultipleDTO();
        request.setName(List.of("coco", "adf", "coco")); // 중복된 사용자 "coco" 포함

        HttpEntity<UserRequest.SaveMultipleDTO> entity = new HttpEntity<>(request, headers);

        // When
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                baseUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(400); // 중복된 사용자로 인해 400 에러 발생 예상
        Map<String, Object> responseBody = response.getBody();

        // 에러 메시지 검증
        assertThat(responseBody.get("reason")).isEqualTo("이미 존재하는 유저입니다: coco");
    }
}