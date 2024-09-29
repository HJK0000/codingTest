package com.example.prac.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {

    @Data
    public static class SaveOneDTO {

        @NotEmpty(message = "이름은 비어 있을 수 없습니다.")
        private String name;

        public User toEntity() {
            return User.builder().name(name).build();
        }

    }

    @Data
    public static class SaveMultipleDTO {

        @NotEmpty(message = "이름 리스트는 비어 있을 수 없습니다.")
        private List<String> name;

        public List<User> toEntity() {
            List<User> users = new ArrayList<>();
            for (String n : name) {
                User user = User.builder().name(n).build(); // 각 이름으로 User 객체 생성
                users.add(user); // List<User>에 추가
            }
            return users;
        }

    }

    @Data
    public static class UpdateDTO {

        @NotEmpty(message = "이름은 비어 있을 수 없습니다.")
        private String name;

    }
}
