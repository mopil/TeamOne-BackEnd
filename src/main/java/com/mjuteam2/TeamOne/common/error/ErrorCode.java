package com.mjuteam2.TeamOne.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ErrorCode {
    public static final String COMMON_ERROR = "common";

    /**
     * 회원가입 오류 (임시)
     */
    public static final String SIGN_UP_ERROR = "signUp";
    public static final String AUTH_TOKEN_ERROR = "authToken";
    public static final String VALIDATION_ERROR = "validation";

    /**
     *  로그인 오류
     */
    public static final String LOGIN_ERROR = "login";


    /**
     * 게시글 오류
     */
    public static final String BOARD_ERROR = "board";


    /**
     * 회원 CRUD 오류
     */
    public static final String MEMBER_CRUD_ERROR = "memberCrud";
}
