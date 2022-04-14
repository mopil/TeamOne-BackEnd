package com.mjuteam2.TeamOne.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class PasswordUpdateForm {

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String newPasswordCheck;
}
