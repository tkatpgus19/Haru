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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        User tmp = new User();
        User result = userService.login(user.getUserId(), user.getUserPassword());
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }

    @PostMapping("/isUsed")
    public ResponseEntity<Boolean> isUsed(@RequestBody User user){
        boolean result = userService.isUsed(user.getUserId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findId")
    public ResponseEntity<String> findId(@RequestParam String userEmail){
        String result = userService.findId(userEmail);
        if(result == null){
            result = "null";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/unregister/{userId}")
    public ResponseEntity<Boolean> unregister(@PathVariable("userId") String userId){
        boolean result = userService.unregister(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody User user){
        boolean result = userService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
