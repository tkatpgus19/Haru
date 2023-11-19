package com.ssafy.haru.api;

import com.ssafy.haru.model.dto.Homework;
import com.ssafy.haru.service.HomeworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homework")
@CrossOrigin("*")
@Slf4j
public class HomeworkApi {

    @Autowired
    HomeworkService homeworkService;

    // 숙제 조회
    @GetMapping("/selectHomework")
    public ResponseEntity<Homework> selectHomework(@RequestParam String userId, @RequestParam String homeworkDate) {
        Homework tmp = new Homework();
        Homework result = homeworkService.selectHomework(userId, homeworkDate);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }

    // 숙제 저장
    @PostMapping("/saveHomework")
    public ResponseEntity<Boolean> saveHomework(@RequestBody Homework homework) {
        boolean result = false;
        if (homeworkService.selectHomework(homework.getUserId(), homework.getHomeworkDate()) == null) {
            result = homeworkService.saveHomework(homework);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 숙제 수정
    @PutMapping("/updateHomework")
    public ResponseEntity<Boolean> updateHomework(@RequestBody Homework homework){
        boolean result = false;
        if (homeworkService.selectHomework(homework.getUserId(), homework.getHomeworkDate()) != null) {
            result = homeworkService.updateHomework(homework);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 숙제 삭제
    @DeleteMapping("/deleteHomework")
    public ResponseEntity<Boolean> deleteHomework(@RequestParam String userId, @RequestParam String homeworkDate){
        boolean result = false;
        if(homeworkService.selectHomework(userId, homeworkDate) != null){
            result = homeworkService.deleteHomework(userId, homeworkDate);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
