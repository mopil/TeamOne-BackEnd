package com.mjuteam2.TeamOne.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class PasswordUpdateForm {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    @Size(min = 6, max = 12, message = "비밀번호는 6~12자리 사이로 설정해주세요")
    private String newPassword;

    @NotEmpty
    private String newPasswordCheck;
}
