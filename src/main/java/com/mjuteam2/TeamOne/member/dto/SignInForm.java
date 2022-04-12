package com.mjuteam2.TeamOne.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@Builder
public class SignInForm {

    @NotEmpty
    private String id;

    @NotEmpty
    private String password;
}
