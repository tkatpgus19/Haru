package com.ssafy.haru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Homework {
    private int homeworkNo;
    private String userId;
    private String homeworkQuestion;
    private String homeworkContent;
    private String homeworkDate;

    @Override
    public String toString() {
        return "Homework{" +
                "homeworkNo=" + homeworkNo +
                ", userId='" + userId + '\'' +
                ", homeworkQuestion='" + homeworkQuestion + '\'' +
                ", homeworkContent='" + homeworkContent + '\'' +
                ", homeworkDate='" + homeworkDate + '\'' +
                '}';
    }
}
