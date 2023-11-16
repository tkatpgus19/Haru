package com.example.demo.controller;

import com.example.demo.model.dto.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody User user){
        boolean result = userService.join(user);
        if(result){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        User result = userService.login(user.getUserId(), user.getUserPassword());
        if(result != null){
            log.warn(user.toString());
            log.warn(result.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
