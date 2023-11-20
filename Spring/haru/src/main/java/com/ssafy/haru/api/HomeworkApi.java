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
    public ResponseEntity<Homework> select(@RequestParam String userId, @RequestParam String homeworkDate) {
        Homework tmp = new Homework();
        Homework result = homeworkService.select(userId, homeworkDate);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }

    // 숙제 저장
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insert(@RequestBody Homework homework) {
        boolean result = false;
        if (homeworkService.select(homework.getUserId(), homework.getHomeworkDate()) == null) {
            result = homeworkService.insert(homework);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 숙제 수정
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Homework homework){
        boolean result = false;
        if (homeworkService.select(homework.getUserId(), homework.getHomeworkDate()) != null) {
            result = homeworkService.update(homework);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 숙제 삭제
    @DeleteMapping("/deleteHomework")
    public ResponseEntity<Boolean> delete(@RequestParam String userId, @RequestParam String homeworkDate){
        boolean result = false;
        if(homeworkService.select(userId, homeworkDate) != null){
            result = homeworkService.delete(userId, homeworkDate);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
