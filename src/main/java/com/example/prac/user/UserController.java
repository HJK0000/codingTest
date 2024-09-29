package com.example.prac.user;


import com.example.prac.util.Resp;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/users")
public class UserController {

    private final HttpSession session;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // TODO : TEST
    @GetMapping("/test")
    public String testAop() {
        return "AOP test";
    }


/*    @PostMapping("/users")
    public ResponseEntity<?> userSave(@RequestBody UserRequest.SaveOneDTO saveDTO) {
        UserResponse.JoinDTO result = userService.회원한명등록(saveDTO);
        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    } */

    @PostMapping("/users")
    public ResponseEntity<?> userSave(@Valid @RequestBody UserRequest.SaveMultipleDTO saveDTO) {
        List<UserResponse.JoinDTO> result = userService.회원여러명등록(saveDTO);
        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> userSelect(@PathVariable("id") Integer id) {
        //logger.info("회원찾기 컨트롤러 실행");
        UserResponse.ListDTO result = userService.회원찾기(id);
        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<?> userUpdate(@PathVariable("id") Integer id, @Valid @RequestBody UserRequest.UpdateDTO updateDTO) {

        UserResponse.UpdateDTO result = userService.회원수정(id, updateDTO);

        return new ResponseEntity<>(Resp.ok(result), HttpStatus.OK);
    }
}
