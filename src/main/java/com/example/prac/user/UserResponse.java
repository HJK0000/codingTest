package com.example.prac.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class JoinDTO {
        private Integer id;

        public JoinDTO(User user) {
            this.id = user.getId();
        }
    }

    @Data
    public static class ListDTO {
        private Integer id;
        private String name;

        public ListDTO(User user) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }

    @Data
    public static class UpdateDTO {
        private Integer id;
        private String name;

        public UpdateDTO(User user) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }
}
