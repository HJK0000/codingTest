package com.example.prac.user;


import com.example.prac.error.ex.ExceptionApi400;
import com.example.prac.error.ex.ExceptionApi404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


/*    @Transactional
    public UserResponse.JoinDTO 회원한명등록(UserRequest.SaveOneDTO saveDTO) {

        Optional<User> userOP = userRepository.findByName(saveDTO.getName());

        if (userOP.isPresent()) {
            throw new ExceptionApi400("이미 존재하는 유저입니다.");
        }

        User userPS = userRepository.save(saveDTO.toEntity());

        return new UserResponse.JoinDTO(userPS);

    }*/



    @Transactional
    public List<UserResponse.JoinDTO> 회원여러명등록(UserRequest.SaveMultipleDTO saveDTO) {

        List<UserResponse.JoinDTO> joinDTOList = new ArrayList<>();

        for (String name : saveDTO.getName()) {
            Optional<User> userOP = userRepository.findByName(name);

            if (userOP.isPresent()) {
                throw new ExceptionApi400("이미 존재하는 유저입니다: " + name);
            }

            User userPS = userRepository.save(User.builder().name(name).build());
            joinDTOList.add(new UserResponse.JoinDTO(userPS));
        }

        return joinDTOList;
    }



    public UserResponse.ListDTO 회원찾기(Integer id) {

        User userPS = userRepository.findById(id).orElseThrow(() -> new ExceptionApi404("회원 정보를 찾을 수 없습니다."));

        return new UserResponse.ListDTO(userPS);

    }

    @Transactional
    public UserResponse.UpdateDTO 회원수정(Integer id, UserRequest.UpdateDTO updateDTO) {
        
        // 조회
        User userPS = userRepository.findById(id).orElseThrow(() -> new ExceptionApi404("회원 정보를 찾을 수 없습니다."));

        userPS.setName(updateDTO.getName()); // 더티체킹

        return new UserResponse.UpdateDTO(userPS);
    }
}
