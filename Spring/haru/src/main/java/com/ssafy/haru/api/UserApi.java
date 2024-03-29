package com.ssafy.haru.api;

import com.ssafy.haru.model.dto.FileConverter;
import com.ssafy.haru.model.dto.UploadFile;
import com.ssafy.haru.model.dto.User;
import com.ssafy.haru.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Slf4j
public class UserApi {

    @Autowired
    UserService userService;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody User user){
        boolean result = userService.join(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        User tmp = new User();
        User result = userService.login(user.getUserId(), user.getUserPassword());
        if(result != null){
            log.info("login 성공");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }

    // 아이디 중복 체크
    @GetMapping("/isUsed")
    public ResponseEntity<Boolean> isUsed(@RequestParam String userId){
        boolean result = userService.isUsed(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 분실 아이디 조회
    @GetMapping("/findId")
    public ResponseEntity<String> findId(@RequestParam String userEmail){
        String result = userService.findId(userEmail);
        if(result == null){
            result = "null";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 계정 탈퇴
    @DeleteMapping("/unregister/{userId}")
    public ResponseEntity<Boolean> unregister(@PathVariable("userId") String userId){
        boolean result = userService.unregister(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 계정 정보 수정
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody User user){
        boolean result = userService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/match")
    public ResponseEntity<User> matchPassword(@RequestParam String userId, @RequestParam String userPassword){
        User result = new User();
        User tmp = userService.matchPassword(userId, userPassword);
        if(tmp != null){
            return new ResponseEntity<>(tmp, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/updateHeart")
    public ResponseEntity<Boolean> updateHeart(@RequestParam String userId, @RequestParam String userHeart){
        return new ResponseEntity<>(userService.updateHeart(userId, userHeart), HttpStatus.OK);
    }

    @PutMapping("/updateImage")
    public ResponseEntity<Boolean> updateImage(@RequestPart(value = "userId") String userId, @RequestPart(value = "userImg", required = false)MultipartFile userImg){
        boolean result = false;
        try{
            if(userImg != null){
                UploadFile ufile = FileConverter.storeFile(userImg, "/user/");
                assert ufile != null;
                result = userService.updateImage(userId, ufile.getStoreImgName());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getImage")
    public ResponseEntity<String> getImage(@RequestParam String userId){
        return new ResponseEntity<>(userService.getImage(userId).getUserImg(), HttpStatus.OK);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<User> getUserInfo(@RequestParam String userId){
        return new ResponseEntity<>(userService.getImage(userId), HttpStatus.OK);
    }
}
