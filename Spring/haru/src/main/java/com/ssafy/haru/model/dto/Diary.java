package com.ssafy.haru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diary {
    private int diaryNo;
    private String userId;
    private String diaryEmotion;
    private String diaryContent;
    private String diaryDate;

    @Override
    public String toString() {
        return "Diary{" +
                "diaryNo=" + diaryNo +
                ", userId='" + userId + '\'' +
                ", diaryEmotion='" + diaryEmotion + '\'' +
                ", diaryContent='" + diaryContent + '\'' +
                ", diaryDate='" + diaryDate + '\'' +
                '}';
    }
}
