package com.ssafy.haru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String userId;
    private String userPassword;
    private String userNickname;
    private String userEmail;
    private int userHeart;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userHeart=" + userHeart +
                '}';
    }
}
