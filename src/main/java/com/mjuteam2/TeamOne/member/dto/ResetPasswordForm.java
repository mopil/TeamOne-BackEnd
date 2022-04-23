package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@Builder
public class ResetPasswordForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String schoolId;
}
