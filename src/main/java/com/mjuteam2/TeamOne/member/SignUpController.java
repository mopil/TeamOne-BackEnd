package com.mjuteam2.TeamOne.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Repository
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class SignUpController {

    private final SignUpService signUpService;

    /**
     * 회원 가입
     * @param signUpForm 클라이언트에서 받아온 회원가입 폼 DTO
     * @param bindingResult 에러 발생시 다 잡아주는 녀석
     * @return 성공시 새로운 멤버 객체 JSON으로 반환, 실패시 오류(검증 실패한 부분)을 반환
     */

    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        Member newMember = signUpService.signUp(signUpForm);
        return ResponseEntity.ok().body(newMember);
    }

    @GetMapping("/nickname-check/{nickname}")
    public boolean nicknameCheck(@PathVariable String nickname) {
        return signUpService.nicknameCheck(nickname);
    }

    @GetMapping("/id-check/{id}")
    public boolean idCheck(@PathVariable String id) {
        return signUpService.idCheck(id);
    }
}
