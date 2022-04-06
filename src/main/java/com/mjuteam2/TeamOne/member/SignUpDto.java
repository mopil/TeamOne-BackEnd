package com.mjuteam2.TeamOne.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data @AllArgsConstructor @Builder
public class SignUpDto {
    
    @NotEmpty
    private String name;

    @NotEmpty
    private String department;

    @NotEmpty
    private int schoolId;

    @NotEmpty
    private String phoneNumber;
    
    @NotEmpty
    @Size(min = 2, max = 10, message = "닉네임은 2~10글자 사이로 설정해주세요")
    private String nickname;
    
    @NotEmpty
    @Size(min = 4, max = 10, message = "아이디는 4~10글자 사이로 설정해주세요")
    private String id;
    
    @NotEmpty
    @Size(min = 6, max = 12, message = "비밀번호는 6~12자리 사이로 설정해주세요")
    private String password;

    @NotEmpty
    private String passwordCheck;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String signUpToken;

    public boolean passwordCheck() {
        return password.equals(passwordCheck);
    }

    public Member toMember() {
        return Member.builder()
                .userId(this.id)
                .userName(this.name)
                .password(this.password)
                .email(this.email)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .department(this.department)
                .schoolId(this.schoolId)
                .signUpToken(this.signUpToken)
                .build();
    }

}
