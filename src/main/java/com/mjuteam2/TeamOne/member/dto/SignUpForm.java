package com.mjuteam2.TeamOne.member.dto;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.domain.MemberType;
import com.mjuteam2.TeamOne.member.domain.MemberValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor @Builder
public class SignUpForm {
    
    @NotEmpty
    private String userName;

    @NotEmpty
    private String department;

    @NotEmpty
    private String schoolId;

    @NotEmpty
    private String phoneNumber;
    
    @NotEmpty
    @Size(min = 2, max = 10, message = "닉네임은 2~10글자 사이로 설정해주세요")
    private String nickname;
    
    @NotEmpty
    @Size(min = 4, max = 10, message = "아이디는 4~10글자 사이로 설정해주세요")
    private String userId;
    
    @NotEmpty
    @Size(min = 6, max = 12, message = "비밀번호는 6~12자리 사이로 설정해주세요")
    private String password;

    @NotEmpty
    private String passwordCheck;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String authToken;

    public boolean passwordCheck() {
        return password.equals(passwordCheck);
    }

    public Member toMember(String encryptedPassword) {
        return Member.builder()
                .userId(this.userId)
                .userName(this.userName)
                .password(encryptedPassword)
                .email(this.email)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .department(this.department)
                .schoolId(this.schoolId)
                .authToken(this.authToken)
                .memberType(MemberType.USER)
                .memberValue(new MemberValue())
                .build();
    }

}
