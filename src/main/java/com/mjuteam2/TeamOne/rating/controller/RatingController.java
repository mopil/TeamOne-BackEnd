package com.mjuteam2.TeamOne.rating.controller;

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
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    /**
     * 팀원 평가하기
     */
    @PostMapping("/{boardId}/{memberId}")
    public ResponseEntity<?> rating(@PathVariable Long boardId,
                                    @PathVariable Long memberId,
                                    @RequestBody RatingForm form) {
        ratingService.save(boardId, memberId, form);
        return success(new BoolResponse(true));
    }
}
