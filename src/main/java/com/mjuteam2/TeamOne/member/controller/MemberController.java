package com.mjuteam2.TeamOne.member.controller;

import com.mjuteam2.TeamOne.common.dto.ApiResponse;
import com.mjuteam2.TeamOne.common.error.ErrorDto;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.dto.PasswordUpdateForm;
import com.mjuteam2.TeamOne.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 하나 조회 id = PK, userId = 진짜 유저 아이디
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        Member foundMember = memberService.findByUserId(id);
        log.info("select member = {}", foundMember);
        return ApiResponse.success(foundMember);
    }

    /**
     * 회원 정보 수정
     */
    // 닉네임 변경
    @PutMapping("/{id}/nickname/{newNickname}")
    public ResponseEntity<?> updateNickname(@PathVariable Long id, @PathVariable String newNickname) {
        return ApiResponse.success(memberService.updateNickname(id, newNickname));
    }

    // 비밀번호 변경
    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id,
                                            @Valid @RequestBody PasswordUpdateForm form,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Password Update Error = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        return ApiResponse.success(memberService.updatePassword(id, form.getNewPassword()));
    }

    // 평점(star) 변경
    @PutMapping("/{id}/star/{newStar}")
    public ResponseEntity<?> updateStar(@PathVariable Long id, @PathVariable Double newStar) {
        return ApiResponse.success(memberService.updateStar(id, newStar));
    }

    // 포인트 변경 (랭킹 포인트)
    @PutMapping("/{id}/point/{newPoint}")
    public ResponseEntity<?> updatePoint(@PathVariable Long id, @PathVariable Integer newPoint) {
        return ApiResponse.success(memberService.updatePoint(id, newPoint));
    }

    // 자기소개 변경
    @PutMapping("/{id}/introduce/{newIntroduce}")
    public ResponseEntity<?> updateIntroduce(@PathVariable Long id, @PathVariable String newIntroduce) {
        return ApiResponse.success(memberService.updateIntroduce(id, newIntroduce));
    }


//    /**
//     * 회원 탈퇴
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        memberService.deleteMember(id);
//        return ApiResponse.success(true);
//    }
}
