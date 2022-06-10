package com.mjuteam2.TeamOne.rating.controller;

import com.mjuteam2.TeamOne.rating.dto.RatingListResponse;
import com.mjuteam2.TeamOne.rating.service.RatingService;
import com.mjuteam2.TeamOne.rating.dto.RatingForm;
import com.mjuteam2.TeamOne.util.dto.BoolResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mjuteam2.TeamOne.util.dto.RestResponse.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    /**
     * 팀원 평가하기
     */
    @PostMapping("")
    public ResponseEntity<?> rating(@RequestBody RatingForm form) {
        ratingService.ratingCreate(form);
        return success(new BoolResponse(true));
    }

    /**
     * 평가 내역 조회 (내가 한 평가)
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<?> ratingList(@PathVariable("memberId") Long memberId) {
        return success(ratingService.findRating(memberId));
    }


    /**
     * 평가 내역 조회 (내가 받은 평가)
     */
    @GetMapping("/rated/{memberId}")
    public ResponseEntity<?> ratedList(@PathVariable("memberId") Long memberId) {
        RatingListResponse rating = ratingService.findRated(memberId);
        return success(rating);
    }

}
