package com.example.prac.user;


import com.example.prac.util.Resp;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final HttpSession session;
    private final UserService userService;

    // TODO : TEST
    @GetMapping("/test")
    public String testAop() {
        return "AOP test";
    }

/*    @PostMapping("/users")
    public ResponseEntity<?> userSave(@RequestBody UserRequest.SaveOneDTO saveDTO) {
        UserResponse.JoinDTO result = userService.회원한명등록(saveDTO);
        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }*/

    @PostMapping("/users")
    public ResponseEntity<?> userSave(@RequestBody UserRequest.SaveMultipleDTO saveDTO) {
        List<UserResponse.JoinDTO> result = userService.회원여러명등록(saveDTO);
        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<?> userSelect(@PathVariable("id") Integer id) {

        UserResponse.ListDTO result = userService.회원찾기(id);
        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> userUpdate(@PathVariable("id") Integer id, @RequestBody UserRequest.UpdateDTO updateDTO) {

        UserResponse.UpdateDTO result = userService.회원수정(id, updateDTO);

        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }
}
