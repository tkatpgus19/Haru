package com.ssafy.haru.api;

import com.ssafy.haru.model.dto.Diary;
import com.ssafy.haru.model.dto.FileConverter;
import com.ssafy.haru.model.dto.UploadFile;
import com.ssafy.haru.service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/diary")
@CrossOrigin("*")
@Slf4j
public class DiaryApi {

    @Autowired
    DiaryService diaryService;

    // 일기 조회
    @GetMapping("/select")
    public ResponseEntity<Diary> select(@RequestParam String userId, @RequestParam String diaryDate){
        Diary tmp = new Diary();
        Diary result = diaryService.select(userId, diaryDate);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }

    // 일기 저장
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insert(@RequestBody Diary diary){
        boolean result = false;
        if(diaryService.select(diary.getUserId(), diary.getDiaryDate()) == null) {
            result = diaryService.insert(diary);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 일기 수정
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Diary diary){
        boolean result = false;
        if(diaryService.select(diary.getUserId(), diary.getDiaryDate()) != null){
            result = diaryService.update(diary);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 일기 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteDiary(@RequestParam String userId, @RequestParam String diaryDate){
        boolean result = false;
        if(diaryService.select(userId, diaryDate) != null){
            result = diaryService.delete(userId, diaryDate);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // 테스트
    @PostMapping("/test")
    public ResponseEntity<Boolean> test(@RequestPart(value = "image")MultipartFile image) throws IOException {

        UploadFile ufile = FileConverter.storeFile(image, "/diary/");
        log.warn(ufile.toString());
//            log.warn(ufile.getStoreImgName());
//            log.warn(ufile.getOriginImgName());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
