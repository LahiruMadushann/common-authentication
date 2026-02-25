package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.PresignedRequestDto;
import com.ctn.commonauthentication.dto.PresignedResponse;
import com.ctn.commonauthentication.serviceImpl.AssessedAppraisalService;
import com.ctn.commonauthentication.serviceImpl.S3Services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController("FileController")
@RequiredArgsConstructor
@RequestMapping("/api/v2/file/presigned")
public class FileController {

    private final S3Services s3Services;
    private final AssessedAppraisalService assessedAppraisalService;
    @PostMapping("")
    public ResponseEntity<PresignedResponse> getPreSignedURL(@RequestBody PresignedRequestDto request) throws UnsupportedEncodingException {
        String url = s3Services.getPreSignedPutUrl(request.getBucketName(), request.getKeyName());
        PresignedResponse response = new PresignedResponse();
        response.setUrl(url);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
