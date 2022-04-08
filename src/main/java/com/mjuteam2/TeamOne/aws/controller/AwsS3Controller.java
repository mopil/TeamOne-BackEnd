package com.mjuteam2.TeamOne.aws.controller;

import com.mjuteam2.TeamOne.common.dto.ApiResponse;
import com.mjuteam2.TeamOne.aws.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @PostMapping("/images")
    public ResponseEntity<List<String>> uploadImage(@RequestPart List<MultipartFile> multipartFile) {
        return ApiResponse.success(awsS3Service.uploadImage(multipartFile));
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping("/images")
    public ResponseEntity<Void> deleteImage(@RequestParam String fileName) {
        awsS3Service.deleteImage(fileName);
        return ApiResponse.success(null);
    }
}
