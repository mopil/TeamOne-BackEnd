package com.mjuteam2.TeamOne.caution.controller;

import com.mjuteam2.TeamOne.caution.dto.CautionListResponse;
import com.mjuteam2.TeamOne.caution.dto.CautionResponse;
import com.mjuteam2.TeamOne.caution.exception.CautionException;
import com.mjuteam2.TeamOne.caution.service.CautionService;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.service.MemberService;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import com.mjuteam2.TeamOne.util.dto.ErrorResponse;
import com.mjuteam2.TeamOne.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.badRequest;
import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cautions")
public class CautionController {

    private final CautionService cautionService;
    private final MemberService memberService;

    /**
     * 사용자 유의 생성
     */
    @PostMapping("/{targetMemberId}")
    public ResponseEntity<?> createCaution(HttpServletRequest request, @PathVariable Long targetMemberId) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        CautionResponse caution = cautionService.setCaution(loginMember.getId(), targetMemberId);
        return success(caution);
    }

    /**
     * 사용자 유의 해제(삭제)
     */
    @DeleteMapping("/{cautionedMemberId}")
    public ResponseEntity<?> deleteCaution(@PathVariable Long cautionedMemberId) {
        cautionService.removeCaution(cautionedMemberId);
        return success(new BoolResponse(true));
    }

    /**
     * 사용자 유의 전체 해제(삭제)
     */
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllCaution(HttpServletRequest request) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        cautionService.removeAllCaution(loginMember.getId());
        return success(new BoolResponse(true));
    }


    /**
     * 사용자 유의 리스트 전체 조회
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll(HttpServletRequest request) throws LoginException {
        Member loginMember = memberService.getLoginMember(request);
        CautionListResponse result = cautionService.findAll(loginMember.getId());
        return success(result);
    }

    /**
     *  예외 처리
     */
    @ExceptionHandler(CautionException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> cautionExHandle(CautionException e) {
        log.error("유의 예외 발생 : 핸들러 작동");
        return badRequest(new ErrorResponse(ErrorCode.CAUTION_ERROR, e.getMessage()));
    }
}
